(function (jq) {

    var DEFAULT_OPTIONS = {
        dataType: 'json'
    };

    var ajax = function (options) {
        $.ajax({
            url: options.url,
            dataType: options.dataType,
            data: options.data,
            xhrFields: {
                withCredentials: true
            },
            contentType: options.contentType,
            type: options.method,
            headers: options.headers,
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

                if (typeof options.callback == 'function') {
                    options.callback(error);
                }
            }
        });
    };


    jq.extend({
        http: {
            "get": function (url, data, callback, ops) {
                var options = $.extend({},
                    {
                        method: 'GET',
                        url: url,
                        data: data,
                        callback: callback
                    },
                    DEFAULT_OPTIONS, ops);
                ajax(options);
            },
            post: function (url, data, callback, ops) {
                var options = $.extend({},
                    {
                        method: 'POST',
                        url: url,
                        data: data,
                        callback: callback
                    },
                    DEFAULT_OPTIONS, ops);
                ajax(options);
            },
            delete: function (url, data, callback, ops) {
                var options = $.extend({
                    method: 'POST',
                    headers: {
                        Hidden_Http_Method: 'delete'
                    },
                    url: url,
                    data: data,
                    callback: callback,

                }, DEFAULT_OPTIONS, ops);
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

