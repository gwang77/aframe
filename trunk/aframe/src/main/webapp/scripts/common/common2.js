//score-bar
function resizescore() {
    var scorebar = $('.table-examresult td .score-bar');
    for (i = 0; i < scorebar.length; i++) {
        var score = parseInt(scorebar.eq(i).find('span').text());
        var totalscore = parseInt(scorebar.eq(i).find('em').text());
        var percentwidth = (score / totalscore) * parseInt(scorebar.width());
        var mainbar = scorebar.eq(i).children('div');
        mainbar.css('width', percentwidth);
        if (score / totalscore < 0.7 && score / totalscore >= 0.4) {
            mainbar.addClass('pass');
        } else if (score / totalscore < 0.4) {
            mainbar.addClass('notpass');
        }
        ;
    }
}
function resizescore2() {
    var scorebar = $('.table-paper td .score-bar');
    for (i = 0; i < scorebar.length; i++) {
        var score = parseInt(scorebar.eq(i).find('span').text());
        var totalscore = parseInt(scorebar.eq(i).find('em').text());
        var percentwidth = (score / totalscore) * parseInt(scorebar.width());
        var mainbar = scorebar.eq(i).children('div');
        mainbar.css('width', percentwidth);
    }
}

$(function () {
    resizescore();
    resizescore2();
    // select
    $('.select-content').on('click', function () {
        $('.search-box ul').hide();
        $('.search-box .arrow-down').removeClass('arrow-up');
        $(this).siblings('ul').show(100);
        $(this).siblings('.arrow-down').addClass('arrow-up');
    });

    $('.search-box ul li').on('click', function () {
        var n = $(this).index();
        var ntext = $(this).text();
        $(this).parent('ul').siblings('.select-content').text(ntext);
        $(this).parent('ul').siblings('select')[0].selectedIndex = n;
        $(this).addClass('active').siblings('li').removeClass('active');
        $(this).parent('ul').hide();
        $(this).parent('ul').siblings('.arrow-down').removeClass('arrow-up');
        if ($(this).parent('ul').siblings('select')[0].onchange != null) {
            $(this).parent('ul').siblings('select')[0].onchange();
        }
    });

    $('.select-content').on('click', function () {
        $('.search-box1 ul').hide();
        $('.search-box1 .arrow-down').removeClass('arrow-up');
        $(this).siblings('ul').show(100);
        $(this).siblings('.arrow-down').addClass('arrow-up');
    });

    $('.search-box1 ul li').on('click', function () {
        var n = $(this).index();
        var ntext = $(this).text();
        $(this).parent('ul').siblings('.select-content').text(ntext);
        $(this).parent('ul').siblings('select')[0].selectedIndex = n;
        $(this).addClass('active').siblings('li').removeClass('active');
        $(this).parent('ul').hide();
        $(this).parent('ul').siblings('.arrow-down').removeClass('arrow-up');
        if ($(this).parent('ul').siblings('select')[0].onchange != null) {
            $(this).parent('ul').siblings('select')[0].onchange();
        }
    });

    $('.radio-btn').on('click', function () {
        $(this).addClass('on').siblings().removeClass('on');
        this.children[0].checked = true;
        if (this.children[0].onclick != null) {
            this.children[0].onclick();
        }
    });
    $('.checkbox').on('click', function () {
        $(this).toggleClass('on');
        this.children[0].checked = !this.children[0].checked
    });

});
