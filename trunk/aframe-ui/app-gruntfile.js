// Generated on 2015-01-21 using generator-angular 0.9.2
'use strict';

// # Globbing
// for performance reasons we're only matching one level down:
// 'test/spec/{,*/}*.js'
// use this if you want to recursively match all subfolders:
// 'test/spec/**/*.js'

module.exports = function (grunt) {

    // Load grunt tasks automatically
    require('load-grunt-tasks')(grunt);

    // Time how long tasks take. Can help when optimizing build times
    require('time-grunt')(grunt);

    // Automatically load required Grunt tasks
    require('jit-grunt')(grunt, {
        useminPrepare: 'grunt-usemin',
        ngtemplates: 'grunt-angular-templates'
    });

    // Configurable paths for the application
    var appConfig = {
        app: require('./bower.json').appPath || 'app',
        appName: require('./bower.json').appName || 'aframeUIApp',
        i18n: require('./bower.json').i18nPath || 'i18n',
        build_env: 'dev',
        dist: 'dist/aframe-acm/dev',
        default_app: 'aframe-acm',
        APP_DEFINE: {
            "aframe-acm": ['aframe-acm-ui'],
        }
    };

    // var server_side = require('./server');

    // For performance reasons, we should assign how many level files should be watched and packed.
    var levelPattern = ',*/,*/*/,*/*/*/,*/*/*/*/';

    // Define the configuration for all the tasks
    grunt.initConfig({

        // Project settings
        yeoman: appConfig,

        // Watches files for changes and runs tasks based on the changed files
        watch: {
            bower: {
                files: ['bower.json'],
                tasks: ['wiredep']
            },
            module: {
                files: ['../*/app/{' + levelPattern + '}*.*'],
                tasks: [
                    'clean:module',
                    'copy:app',
                    'copyModules',
                    'copy:serve',
                    'mergeModules',
                    'newer:jshint:all'
                ],
                options: {
                    livereload: '<%= connect.options.livereload %>'
                }
            },
            /*js: {
                files: [
                    '../!*!/app/scripts/{' + levelPattern + '}*.js',
                    '<%= yeoman.app %>/scripts/{' + levelPattern + '}*.js',
                    '<%= yeoman.app %>/bootstrap/{' + levelPattern + '}*.js',
                    '<%= yeoman.app %>/vendors/{' + levelPattern + '}*.js'
                ],
                tasks: [
                    'clean:module',
                    'copy:app',
                    'copy:module',
                    'copy:serve',
                    'mergeModules',

                ],
                options: {
                    livereload: '<%= connect.options.livereload %>'
                }
            },*/
            jsTest: {
                files: ['test/spec/{' + levelPattern + '}*.js'],
                tasks: ['newer:jshint:test', 'karma']
            },
            styles: {
                files: [
                    '../*/app/styles/{' + levelPattern + '}*.css',
                    '<%= yeoman.app %>/styles/{' + levelPattern + '}*.css',
                    '<%= yeoman.app %>/bootstrap/{' + levelPattern + '}*.css',
                    '<%= yeoman.app %>/vendors/{' + levelPattern + '}*.css'
                ],
                tasks: ['newer:copy:styles', 'autoprefixer']
            },
            gruntfile: {
                files: ['app-gruntfile.js']
            },
            livereload: {
                options: {
                    livereload: '<%= connect.options.livereload %>'
                },
                files: [
                    '../*/app/views/{' + levelPattern + '}*.html',
                    '<%= yeoman.app %>/{' + levelPattern + '}*.html',
                    '<%= yeoman.app %>/views/{' + levelPattern + '}*.html',
                    '.tmp/styles/{' + levelPattern + '}*.css',
                    '.tmp/bootstrap/{' + levelPattern + '}*.css',
                    '<%= yeoman.app %>/images/{' + levelPattern + '}*.{png,jpg,jpeg,gif,webp,svg}'
                ]
            }
        },

        // The actual grunt server settings
        connect: {
            options: {
                port: 9095,
                // Change this to '0.0.0.0' to access the server from outside.
                hostname: 'localhost',
                livereload: 35735
            },
            livereload: {
                options: {
                    open: true,
                    middleware: function (connect) {
                        var middlewares = [
                            connect.static('.tmp'),
                            connect().use('/bower_components', connect.static('./bower_components')),
                            connect.static('.tmp/app')
                        ];
                        // server_side(connect, middlewares);

                        return middlewares;
                    }
                }
            },
            test: {
                options: {
                    port: 9001,
                    middleware: function (connect) {
                        var middlewares = [
                            connect.static('.tmp'),
                            connect.static('test'),
                            connect().use('/bower_components', connect.static('./bower_components')),
                            connect.static('.tmp/app')
                        ];
                        // server_side(connect, middlewares);

                        return middlewares;
                    }
                }
            },
            dist: {
                options: {
                    open: true,
                    base: '<%= yeoman.dist %>'
                }
            }
        },

        // Make sure code styles are up to par and there are no obvious mistakes
        jshint: {
            options: {
                jshintrc: '.jshintrc',
                reporter: require('jshint-stylish')
            },
            all: {
                src: [
                    'app-gruntfile.js',
                    '../aframe-ui/app/scripts/{' + levelPattern + '}*.js',
                    '../{<%= yeoman.APP_DEFINE[yeoman.default_app] %>,}/app/scripts/{' + levelPattern + '}*.js',
                    '!../aframe-ui/app/scripts/app.js',
                    '!../aframe-ui/app/scripts/config.js',
                    '!../aframe-ui/app/scripts/common/itrust/controllers/logout.ctrl.js',
                    '!../aframe-ui/<%= yeoman.dist %>/scripts/*.js'
                ]
            },
            test: {
                options: {
                    jshintrc: 'test/.jshintrc'
                },
                src: ['test/spec/{' + levelPattern + '}*.js']
            }
        },

        // Empties folders to start fresh
        clean: {
            dist: {
                files: [{
                    dot: true,
                    src: [
                        '.tmp',
                        '<%= yeoman.dist %>'
                        /*'<%= yeoman.dist %>/{' + levelPattern + '}*',
                        '!<%= yeoman.dist %>/.git*'*/
                    ]
                }]
            },
            server: {
            },
            module: {
                files: [{
                    dot: true,
                    src: [
                        '.tmp/app'
                    ]
                }]
            }
        },

        // Add vendor prefixed styles
        autoprefixer: {
            options: {
                browsers: ['last 1 version']
            },
            dist: {
                files: [{
                    expand: true,
                    cwd: '.tmp/styles/',
                    src: '{' + levelPattern + '}*.css',
                    dest: '.tmp/styles/'
                }]
            }
        },

        // Automatically inject Bower components into the app
        wiredep: {
            app: {
                src: ['<%= yeoman.app %>/index.html'],
                ignorePath: /\.\.\//
            }
        },

        // Renames files for browser caching purposes, e.g. css/app.css -> css/ae35dd05.app.css
        filerev: {
            dist: {
                src: [
                    '<%= yeoman.dist %>/scripts/{' + levelPattern + '}*.js',
                    '<%= yeoman.dist %>/styles/*.css'
                ]
            }
        },

        // Reads HTML for usemin blocks to enable smart builds that automatically
        // concat, minify and revision files. Creates configurations in memory so
        // additional tasks can operate on them
        useminPrepare: {
            html: '.tmp/app/index.html',
            options: {
                dest: '<%= yeoman.dist %>',
                //root: '.tmp,.tmp/app',
                flow: {
                    html: {
                        steps: {
                            js: ['concat', 'uglifyjs'],
                            css: ['cssmin']
                        },
                        post: {}
                    }
                }
            }
        },

        // Performs rewrites based on filerev and the useminPrepare configuration
        usemin: {
            html: ['<%= yeoman.dist %>/{' + levelPattern + '}*.html'],
            css: ['<%= yeoman.dist %>/styles/{' + levelPattern + '}*.css'],
            js: ['<%= yeoman.dist %>/scripts/{' + levelPattern + '}*.js'],
            options: {
                assetsDirs: [
                    '<%= yeoman.dist %>',
                    '<%= yeoman.dist %>/images',
                    '<%= yeoman.dist %>/styles'
                ],
                patterns: {
                    js: [[/(images\/[^''""]*\.(png|jpg|jpeg|gif|webp|svg))/g, 'Replacing references to images']]
                }
            }
        },

        // The following *-min tasks will produce minified files in the dist folder
        // By default, your `index.html`'s <!-- Usemin block --> will take care of
        // minification. These next options are pre-configured if you do not wish
        // to use the Usemin blocks.
        //cssmin: {
        //    dist: {
        //        files: [{
        //            expand: true,
        //            cwd: '<%= yeoman.dist %>',
        //            src: 'styles/*.css',
        //            dest: '<%= yeoman.dist %>'
        //        }]
        //    }
        //},
        //uglify: {
        //    build: {
        //        files: [{
        //            expand: true,
        //            src: '**/*.js',
        //            dest: '<%= yeoman.dist %>/scripts',
        //            cwd: '<%= yeoman.app %>/scripts'
        //        }]
        //    },
        //    options: {
        //        mangle: false
        //    },
        //},
        // concat: {
        //   dist: {}
        // },
        /*concat: {
            dist: {
                src: ['.tmp/app/scripts/{' + levelPattern + '}*.js'],
                dest: '.tmp/concat/scripts/scripts.js'
            }

        },*/

        imagemin: {
            dist: {
                files: [{
                    expand: true,
                    cwd: '.tmp/app/images',
                    src: '{' + levelPattern + '}*.{png,jpg,jpeg,gif}',
                    dest: '<%= yeoman.dist %>/images'
                }]
            }
        },

        svgmin: {
            dist: {
                files: [{
                    expand: true,
                    cwd: '.tmp/app/images',
                    src: '{' + levelPattern + '}*.svg',
                    dest: '<%= yeoman.dist %>/images'
                }]
            }
        },

        htmlmin: {
            dist: {
                options: {
                    collapseWhitespace: true,
                    conservativeCollapse: true,
                    collapseBooleanAttributes: true,
                    removeCommentsFromCDATA: true,
                    removeOptionalTags: true
                },
                files: [{
                    expand: true,
                    cwd: '<%= yeoman.dist %>',
                    src: '**/*.html',
                    dest: '<%= yeoman.dist %>'
                }]
            }
        },

        ngtemplates: {
            dist: {
                options: {
                    module: '<%= yeoman.appName %>',
                    htmlmin: '<%= htmlmin.dist.options %>',
                    usemin: 'scripts/scripts.js'
                },
                cwd: '.tmp/app',
                src: 'views/{' + levelPattern + '}*.html',
                dest: '.tmp/templateCache.js'
            }
        },

        // ngAnnotate tries to make the code safe for minification automatically by
        // using the Angular long form for dependency injection. It doesn't work on
        // things like resolve or inject so those have to be done manually.
        ngAnnotate: {
            dist: {
                files: [{
                    expand: true,
                    cwd: '.tmp/concat/scripts',
                    src: '*.js',
                    dest: '<%= yeoman.dist %>/scripts',
                }]
            }
        },

        // Replace Google CDN references
        cdnify: {
            dist: {
                html: ['<%= yeoman.dist %>/*.html']
            }
        },

        // Copies remaining files to places other tasks can use
        copy: {
            const_env: {
                files: [{
                    expand: true,
                    cwd: './config/environments/<%= yeoman.default_app %>/<%= yeoman.build_env %>',
                    dest: '<%= yeoman.app %>/scripts',
                    src: 'const_env.js'
                }]
            },
            dist: {
                files: [{
                    expand: true, dot: true,
                    cwd: '.tmp/app',
                    dest: '<%= yeoman.dist %>',
                    src: [
                        '*.{ico,png,txt}',
                        '.htaccess',
                        '*.html',
                        'images/{' + levelPattern + '}*.*',
                        'i18n/{' + levelPattern + '}*.*',
                        'i18n/**/aframe.lang.json',
                        'i18n/**/aframe-ext.lang.json',
                        'scripts/const_env.js',
                        'scripts/config.js'
                    ]
                }, {
                    expand: true,
                    cwd: '.tmp/images',
                    dest: '<%= yeoman.dist %>/images',
                    src: ['generated/*']
                }, {
                    expand: true,
                    cwd: '.tmp/app/bootstrap',
                    src: 'fonts/*',
                    dest: '<%= yeoman.dist %>'
                }, {
                    expand: true,
                    cwd: 'bower_components',
                    dest: '<%= yeoman.dist %>/fonts',
                    src: ['**/fonts/*'],
                    flatten: true
                }, {
                    expand: true,
                    cwd: 'bower_components',
                    dest: '<%= yeoman.dist %>/img',
                    src: ['**/img/*'],
                    flatten: true
                //}, {
                //    expand: true,
                //    cwd: 'bower_components/aframe-components/i18n',
                //    dest: '<%= yeoman.dist %>/<%= yeoman.i18n %>',
                //    src: '{' + levelPattern + '}*.*'
                //}, {
                //    expand: true,
                //    cwd: 'bower_components/aframe-components-ext/i18n',
                //    dest: '<%= yeoman.dist %>/<%= yeoman.i18n %>',
                //    src: '{' + levelPattern + '}*.*'
                }]
            },
            styles: {
                files: [{
                    expand: true,
                    cwd: '.tmp/app/styles',
                    dest: '.tmp/styles/',
                    src: '{' + levelPattern + '}*.css'
                }, {
                    expand: true,
                    cwd: '.tmp/app/bootstrap',
                    dest: '.tmp/bootstrap/',
                    src: '{' + levelPattern + '}*.css'
                }]
            },
            serve: {
                files: [{
                    expand: true,
                    cwd: '<%= yeoman.app %>/aframe-components/i18n',
                    dest: '.tmp/app/i18n',
                    src: '{' + levelPattern + '}*.*'
                }, {
                    expand: true,
                    cwd: '<%= yeoman.app %>/aframe-components-ext/i18n',
                    dest: '.tmp/app/i18n',
                    src: '{' + levelPattern + '}*.*'
                }]
            },
            app: {
                files: [{
                    expand: true,
                    cwd: '<%= yeoman.app %>',
                    dest: '.tmp/app',
                    src: [
                        '{' + levelPattern + '}*.*'
                    ]
                }]
            },
            module: {
                files: grunt.file.expand(['../{' + appConfig.APP_DEFINE[appConfig.default_app].toString() + ',}/app']).map(function (cwd) {
                    return {
                        expand: true,
                        cwd: cwd,
                        dest: '.tmp/app',
                        src: [
                            '{' + levelPattern + '}*.*',
                            '!app.json'
                        ]
                    };
                })
            },
            module_single: {
                files: [
                    {
                        expand: true,
                        cwd: '../<%= module_name %>/app',
                        dest: '.tmp/app',
                        src: [
                            '{' + levelPattern + '}*.*',
                            '!app.json'
                        ]
                    }
                ]
            }
        },

        // Run some tasks in parallel to speed up the build process
        concurrent: {
            server: [
                'copy:styles'
            ],
            test: [
                'copy:styles'
            ],
            dist: [
                'copy:styles'
                //'imagemin',
                //'svgmin'
            ]
        },

        // Test settings
        karma: {
            unit: {
                configFile: 'test/karma.conf.js',
                singleRun: true
            }
        }
    });

    var title_place_holder = "<title>.*</title>";
    function mergeCode(src, moduleName, placeHolder) {
        var app = require('../' + moduleName + '/app/app.json');
        var mergeContent = '',
            file,
            fileContent;

        if (src === '.tmp/app/index.html' && app.index !== undefined) {
            if (placeHolder === '<!--mergepartScripts-->' && app.index.script !== undefined && app.index.script.length > 0) {
                for (var i = 0; i < app.index.script.length; i++) {
                    mergeContent += '<script src="' + app.index.script[i] + '"></script>' + '\r\n' + '\t';
                }
            } else if (placeHolder === '<!--mergepartStyles-->' && app.index.css !== undefined && app.index.css.length > 0) {
                for (var c = 0; c < app.index.css.length; c++) {
                    mergeContent += '<link rel="stylesheet" href="' + app.index.css[c] + '">' + '\r\n' + '\t';
                }
            } else if (placeHolder === title_place_holder && app.index.title !== undefined && app.index.title !== null && app.index.title !== '') {
                mergeContent = "<title>" + app.index.title + "</title>";
            }

        } else if (src === '.tmp/app/scripts/app.js' && app.app !== undefined && app.app.length > 0) {
            for (var j = 0; j < app.app.length; j++) {
                mergeContent += '"' + app.app[j] + '",' + '\n' + '\t';
            }
        } else if (src === '.tmp/app/scripts/config.js' && app.config !== undefined) {
            mergeContent = traverse("", app.config, 3);
        }
        if (!mergeContent) {
            return;
        }
        //console.log(mergeContent);
        if (placeHolder !== title_place_holder) {
            mergeContent += placeHolder;
        }
        file = grunt.file.read(src);
        if (placeHolder === title_place_holder) {
            placeHolder = new RegExp(placeHolder);
        }
        fileContent = file.replace(placeHolder, mergeContent);
        grunt.file.write(src, fileContent);

    }

    //for merge config.js
    function traverse(result, obj, level) {
        for (var item in obj) {
            result = appendSpace(result, level);
            if (typeof(obj[item]) === "object") {
                result = result + '"' + item + '":{' + '\n';
                result = traverse(result, obj[item], level + 1);
                result = appendSpace(result, level);
                result = result + '}';
            } else {
                result = result + '"' + item + '"' + ': root_restful + "' + obj[item] + '"';
            }
            result = result + ',\n';
        }
        return result;
    }

    function appendSpace(result, cnt) {
        for (var k = 0; k < cnt; k++) {
            result = result + "    ";
        }
        return result;
    }


    function mergeJson(src, src2, destSrc) {
        var file1 = grunt.file.read(src);
        var file2 = grunt.file.read(src2);
        file1 = file1.substring(0, file1.length - 1).trim() + ',';
        file2 = file2.substring(1, file2.length);
        var contentString = file1 + file2;
        grunt.file.write(destSrc, contentString);
    }

    function mergeModule(moduleName) {

        mergeCode('.tmp/app/index.html', moduleName, '<!--mergepartScripts-->');
        mergeCode('.tmp/app/index.html', moduleName, '<!--mergepartStyles-->');
        mergeCode('.tmp/app/index.html', moduleName, title_place_holder);
        mergeCode('.tmp/app/scripts/app.js', moduleName, '/*mergepart*/');
        mergeCode('.tmp/app/scripts/config.js', moduleName, '/*mergepart*/');

        //mergeJson('app/i18n/en-US/aframeUIApp.lang.json', '../' + moduleName+ '/app/i18n/en-US/aframeUIApp.lang.json', '.tmp/app/i18n/en-US/aframeUIApp.lang.json');
        //mergeJson('app/i18n/zh-CN/aframeUIApp.lang.json', '../' + moduleName+ '/app/i18n/zh-CN/aframeUIApp.lang.json', '.tmp/app/i18n/zh-CN/aframeUIApp.lang.json');

         /*if(moduleName === 'base'){
             mergeJson('app/i18n/en-US/aframeUIApp.lang.json', '../' + moduleName+ '/app/i18n/en-US/aframeUIApp.lang.json', '.tmp/app/i18n/en-US/aframeUIApp.lang.json');
             mergeJson('app/i18n/zh-CN/aframeUIApp.lang.json', '../' + moduleName+ '/app/i18n/zh-CN/aframeUIApp.lang.json', '.tmp/app/i18n/zh-CN/aframeUIApp.lang.json');
         }else{
             grunt.file.copy('../' + moduleName+ '/app/i18n/en-US/aframeUIApp.lang.json', '.tmp/app/i18n/en-US/' + moduleName+ '.lang.json');
             grunt.file.copy('../' + moduleName+ '/app/i18n/zh-CN/aframeUIApp.lang.json', '.tmp/app/i18n/zh-CN/' + moduleName+ '.lang.json');
         }*/
    }

    grunt.registerTask('mergeModules', 'merge modules when build', function (application_name) {
            grunt.log.writeln(this.name, application_name);
            var appl_n = application_name;
            if (!appl_n) {
                appl_n = appConfig.default_app;
            }
            for (var item in appConfig.APP_DEFINE[appl_n]) {
                mergeModule(appConfig.APP_DEFINE[appl_n][item]);
            }
        }
    );

    grunt.registerTask('copyModules', 'copy modules code to .tmp', function (application_name) {
            grunt.log.writeln(this.name, application_name);
            var appl_n = application_name;
            if (!appl_n) {
                appl_n = appConfig.default_app;
            }
            for (var item in appConfig.APP_DEFINE[appl_n]) {
                grunt.log.writeln(appConfig.APP_DEFINE[appl_n][item]);
                grunt.task.run(['prepareCopyModules:' + appConfig.APP_DEFINE[appl_n][item], 'copy:module_single']);
            }
        }
    );

    grunt.registerTask('prepareCopyModules', 'prepare copy modules code to .tmp', function (module_name) {
            grunt.log.writeln(module_name);
            grunt.config.set('module_name', module_name);
        }
    );

    grunt.registerTask('serve', 'Compile then start a connect web server', function (target) {
        if (target === 'dist') {
            return grunt.task.run(['build', 'connect:dist:keepalive']);
        } else {
            if (target) {
                appConfig.default_app = target;
            }
        }

        grunt.task.run([
            'jshint:all',
            'clean:server',
            'wiredep',
            'copy_env:' + appConfig.default_app,
            'copy:app',
            'copyModules:' + appConfig.default_app,
            'mergeModules:' + appConfig.default_app,
            'concurrent:server',
            'autoprefixer',
            'copy:serve',
            'connect:livereload',
            'watch'
        ]);
    });

    grunt.registerTask('test', [
        'jshint:all',
        'clean:server',
        'wiredep',
        'concurrent:test',
        'autoprefixer',
        'connect:test',
        'karma'
    ]);

    grunt.registerTask('copy_env', 'Env configuration', function (application_name, env) {
        grunt.log.writeln(this.name, application_name, env);
        if (env) {
            appConfig.build_env = env;
        }
        if (application_name) {
            appConfig.default_app = application_name;
        }
        grunt.task.run([
            'copy:const_env'
        ]);
    });

    grunt.registerTask('build', '', function (application_name, env) {
        grunt.log.writeln(this.name, application_name, env);
        if (application_name) {
            appConfig.default_app = application_name;
        }
        if (env) {
            appConfig.build_env = env;
            appConfig.dist = "dist" + "/" + appConfig.default_app + "/" + env;
        }
        grunt.task.run([
            'jshint:all',
            'clean:dist',
            'wiredep',
            'copy_env:' + appConfig.default_app + ':' + appConfig.build_env,
            'copy:app',
            'copyModules:' + appConfig.default_app,
            'mergeModules:' + appConfig.default_app,
            'useminPrepare',
            'concurrent:dist',
            'autoprefixer',
            'ngtemplates',
            'concat',
            'ngAnnotate',
            'copy:dist',
            'cssmin',
            'uglify',
            'filerev',
            'usemin',
            'htmlmin'
        ]);
    });

    grunt.registerTask('default', [
        //'newer:jshint',
        //'test',
        'build'
    ]);

};
