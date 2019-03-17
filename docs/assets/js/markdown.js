$(document).ready(function () {
    tableAddClass();
});

function tableAddClass() {
    $('table').each(function () {
        $(this).addClass('table table-striped table-bordered table-hover dataTable dtr-inline');
    })
}
