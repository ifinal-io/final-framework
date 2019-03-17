$(document).ready(function () {
    formatAllLink();
    openHttpLinkInBlank();
    tableAddClass();

});

function tableAddClass() {
    $('table').each(function () {
        $(this).addClass('table table-striped table-bordered table-hover dataTable dtr-inline');
    })
}

//为超链接加上target='_blank'属性
function openHttpLinkInBlank() {
    $('a[href^="http"]').each(function () {
        $(this).attr('target', '_blank');
    });
}

/**
 * 替换页面中所有的<a href="*.md">的为<a href="*.html">
 */
function formatAllLink() {

    let ip = returnCitySN["cip"];
    console.log(returnCitySN["cip"] + ',' + returnCitySN["cname"]);
    let domain = "https://github.com/likly/final-framework/blob/master";
    $.ajax({
        url: 'http://ip.taobao.com/service/getIpInfo.php?ip=' + ip,
        async: false,
        success: function (result) {
            console.log(result);
            let countryId = result.data.country_id;
            if (countryId == 'CN') {
                domain = "https://coding.net/u/likly/p/final-framework/git/blob/master";
            }

        }
    });

    $('a').each(function () {
        let github = "https://github.com/likly/final-framework/blob/master";
        let $a = $(this);
        let href = $a.attr('href');
        if (href !== undefined && href.indexOf('.md') !== -1) {
            console.log(href);
            $a.attr('href', href.substr(0, href.length - 3).concat('.html'));
        } else if (href !== undefined && href.indexOf('.java') !== -1) {
            $a.attr('href', github + href);
        }
    });
}
