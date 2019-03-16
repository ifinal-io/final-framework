$(document).ready(function () {
    replaceAllMarkdownLink();
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
function replaceAllMarkdownLink() {
    $('a').each(function () {
        let $a = $(this);
        let href = $a.attr('href');
        if (href !== undefined && href.indexOf('.md') !== -1) {
            console.log(href);
            $a.attr('href', href.substr(0, href.length - 3).concat('.html'));
        }
    });
}
