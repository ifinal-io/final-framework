(function (jq) {

    var DEFAULT_OPTIONS = {
        dataType: 'json',
        host: ''
    };

    var ajax = function (options) {
        $.ajax({
            url: options.host + options.url,
            dataType: options.dataType,
            data: options.data,
            xhrFields: {
                withCredentials: true
            },
            contentType: options.contentType,
            type: options.method,
            cache: false,
            success: function (result) {
                console.log(result);
                if (typeof options.callback == 'function') {
                    options.callback(result);
                }
            },
            error: function () {
                var error = {
                    status: -1,
                    message: "AJAX ERROR"
                };

                options.callback(error);
            }
        });
    };


    jq.extend({
        http: {
            "get": function (ops) {
                var options = $.extend({},
                    {
                        method: 'GET'
                    },
                    DEFAULT_OPTIONS, ops);
                ajax(options);
            },
            "post": function (ops) {
                var options = $.extend({},
                    {
                        method: 'POST'
                    },
                    DEFAULT_OPTIONS,
                    ops);
                ajax(options);
            },
            send: function (ops) {
                var options = $.extend({},
                    DEFAULT_OPTIONS,
                    ops);
                ajax(options);
            }
        },
        query: {
            "string": function (name) {
                let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                let r = window.location.search.substr(1).match(reg);
                if (r != null) return unescape(r[2]);
                return null;
            }
        }
    })
})(jQuery);

