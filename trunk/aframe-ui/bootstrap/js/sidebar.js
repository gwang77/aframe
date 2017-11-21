+function ($) {
    'use strict';

    var handleContentHeight = function () {
        var headerHeight = $('.page-header-wrapper').outerHeight();
        var footerHeight = $('.page-footer-wrapper').outerHeight();
        var sidebarHeight = $('.page-sidebar-wrapper').outerHeight();
        var contentHeight = ((window.innerHeight > 0) ? window.innerHeight : screen.height) - 1;
        var height = contentHeight - footerHeight - headerHeight;
        height = Math.max(sidebarHeight, height);
        $(".page-content-wrapper").css("min-height", (height) + "px");
    };

    $(window).bind("load resize", function () {
        handleContentHeight();
    });

    $('.page-sidebar-menu a').click(function(){
        $(this).parent().find('.sub-menu').slideToggle(200, handleContentHeight);
    });
}(jQuery);