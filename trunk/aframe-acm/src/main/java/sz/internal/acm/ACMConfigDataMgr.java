package sz.internal.acm;

import org.apache.log4j.Logger;

import sz.internal.acm.service.DelegateService;
import sz.internal.acm.service.UserService;
import sz.internal.acm.to.DelegateTO;
import sz.internal.acm.to.PermissionTO;
import sz.internal.acm.to.UserTO;
import sz.internal.common.SpringContextHolder;

import java.util.*;

public class ACMConfigDataMgr {
	private static final Logger logger = Logger.getLogger(ACMConfigDataMgr.class);
	private static Map<String, Map<String, Map<String, Collection>>> acm_cache = new HashMap<String, Map<String, Map<String, Collection>>>();

	private synchronized static Map<String, Collection> getCacheObject(String app_id, String cacheKey) {
		if (acm_cache == null) {
			acm_cache = new HashMap<String, Map<String, Map<String, Collection>>>();
		}
		Map<String, Map<String, Collection>> app_cache_map = acm_cache.get(app_id);
		if (app_cache_map == null) {
			app_cache_map = new HashMap<String, Map<String, Collection>>();
			acm_cache.put(app_id, app_cache_map);
		}

		Map<String, Collection> cache_map = app_cache_map.get(cacheKey);
		if (cache_map == null) {
			cache_map = new HashMap<String, Collection>();
			app_cache_map.put(cacheKey, cache_map);
		}

		return cache_map;
	}

	public synchronized static void clearCache() {
		acm_cache = new HashMap<String, Map<String, Map<String, Collection>>>();
	}

	public synchronized static void clearCacheRoles(String app_id) {
		acm_cache.get(app_id).put("user_roles", new HashMap<String, Collection>());
	}

	private synchronized static void setCacheRoles(String userName, String app_id, Set<String> valueSet) {
		getCacheObject(app_id, "user_roles").put(userName, valueSet);
	}

	public synchronized static void clearCachePermissions(String app_id) {
		acm_cache.get(app_id).put("user_permissions", new HashMap<String, Collection>());
	}

	private synchronized static void setCachePermissions(String userName, String app_id, Set<String> valueSet) {
		getCacheObject(app_id, "user_permissions").put(userName, valueSet);
	}

	public synchronized static void clearCacheResources(String app_id) {
		acm_cache.get(app_id).put("user_resources", new HashMap<String, Collection>());
	}

	private synchronized static void setCacheResources(String userName, String app_id, List<PermissionTO> valueSet) {
		getCacheObject(app_id, "user_resources").put(userName, valueSet);
	}

	private static List<DelegateTO> getDelegatedUser(String userName) {
		DelegateTO delegateTO = new DelegateTO();
		delegateTO.setUser_from(userName);
		List<DelegateTO> userList = null;
		try {
			DelegateService delegateService = SpringContextHolder.getBean("delegateService");
			userList = delegateService.searchDelegate(delegateTO);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return userList;
	}

	public static Set<String> getUserRolesByUserName(String userName, String app_id) {
		Set<String> results = getSingleUserRolesByUserName(userName, app_id);
		if (results == null) {
			results = new HashSet<String>();
		}
		List<DelegateTO> userList = getDelegatedUser(userName);
		if (userList != null) {
			for (DelegateTO delegateTmp : userList) {
				results.addAll(getSingleUserRolesByUserName(delegateTmp.getUser_to(), app_id));
			}
		}
		return results;
	}

	private static Set<String> getSingleUserRolesByUserName(String userName, String app_id) {
		Set<String> results = (Set<String>) getCacheObject(app_id, "user_roles").get(userName);
		if (results != null) {
			return results;
		}
		try {
			UserService userService = SpringContextHolder.getBean("userService");
			results = userService.findRoles(userName, app_id);
			if (results != null) {
				setCacheRoles(userName, app_id, results);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return results;
	}

	public static Set<String> getUserPermissionsByUserName(String userName, String app_id) {
		Set<String> results = getSingleUserPermissionsByUserName(userName, app_id);
		if (results == null) {
			results = new HashSet<String>();
		}
		List<DelegateTO> userList = getDelegatedUser(userName);
		if (userList != null) {
			for (DelegateTO delegateTmp : userList) {
				results.addAll(getSingleUserPermissionsByUserName(delegateTmp.getUser_to(), app_id));
			}
		}
		return results;
	}

	private static Set<String> getSingleUserPermissionsByUserName(String userName, String app_id) {
		Set<String> results = (Set<String>) getCacheObject(app_id, "user_permissions").get(userName);
		if (results != null) {
			return results;
		}
		try {
			UserService userService = SpringContextHolder.getBean("userService");
			results = userService.findPermissions(userName, app_id);
			if (results != null) {
				setCachePermissions(userName, app_id, results);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return results;
	}

	public static List<PermissionTO> getUserResourcesByUserName(String userName, String app_id) {
		List<PermissionTO> results = getSingleUserResourcesByUserName(userName, app_id);
		if (results == null) {
			results = new ArrayList<PermissionTO>();
		}
		List<DelegateTO> userList = getDelegatedUser(userName);
		if (userList != null) {
			for (DelegateTO delegateTmp : userList) {
				results.addAll(getSingleUserResourcesByUserName(delegateTmp.getUser_to(), app_id));
			}
		}
		if (results != null) {
			for (int i = results.size() - 1; i > 0; i--) {
				PermissionTO permissionTmp1 = results.get(i);
				for (int j = i - 1; j >= 0; j--) {
					PermissionTO permissionTmp2 = results.get(j);
					if (permissionTmp1.getId().equals(permissionTmp2.getId())) {
						results.remove(i);
						break;
					}
				}
			}
		}
		return results;
	}

	private static List<PermissionTO> getSingleUserResourcesByUserName(String userName, String app_id) {
		List<PermissionTO> results = (List<PermissionTO>) getCacheObject(app_id, "user_resources").get(userName);
		if (results != null) {
			return results;
		}
		try {
			UserService userService = SpringContextHolder.getBean("userService");
			results = userService.getPermissionByUsername(userName, app_id);
			if (results != null) {
				setCacheResources(userName, app_id, results);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return results;
	}

}
