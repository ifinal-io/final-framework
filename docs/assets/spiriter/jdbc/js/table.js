Date.prototype.format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "H+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}


let Table = new Vue({
    el: '#dashboard',
    data: {
        modules: [
            {
                id: 'documents',
                link: '/documents',
                logo: '/assets/img/modules/json.png',
                name: 'Final Framework',
                subtitle: 'Final Framework',
                description: '站在巨人的肩膀上，视野更宽广。'
            }
        ],
        sidebar: {
            logo: '/assets/img/modules/json.png',
            name: 'RequestMapping',
            menus: [
                {
                    id: 'requestMapping',
                    name: 'RequestMappings',
                    icon: 'table_chart',
                    link: '/dashboard/request/mappings'
                }
            ],

        },
        page: {
            menus: [],
            module: 'requestMapping'
        },
        filter: '',
        host: 'http://localhost:8080',
        sql: '',
        loggers: [],
        sqlEditor: null,
        resultSet: null
    },
    mounted: function () {
        let _this = this;
        // let sqlEditor = CodeMirror.fromTextArea(document.getElementById('sqlTextArea', {
        //     lineNumbers: true,
        //     theme: 'solarized dark',
        //     mode: {name: "text/x-mysql"},
        //     extraKeys: {
        //         "Ctrl-Enter": "autocomplete",
        //     },
        //     hint: CodeMirror.hint.sql,
        //     hintOptions: {
        //         tables: {
        //             person: ['id', 'name', 'age']
        //         }
        //     }
        // }));
        // sqlEditor.setOption("theme", 'solarized dark');
        // sqlEditor.setOption("lineNumbers", true);
        // sqlEditor.setOption("mode", {name: "text/x-mysql"});
        // // sqlEditor.setOption("hint", CodeMirror.hint.sql);
        // sqlEditor.setOption("hintOptions", {
        //     tables: {
        //         person: ['id', 'name', 'age']
        //     }
        // });


        // sqlEditor.on("cursorActivity", function (editor, change) {
        //     //获取用户当前的编辑器中的编写的代码
        //     var words = editor.getValue() + "";
        //     //利用正则取出用户输入的所有的英文的字母
        //     words = words.replace(/[a-z]+[\-|\']+[a-z]+/ig, '').match(/([a-z]+)/ig);
        //     //将获取到的用户的单词传入CodeMirror,并在javascript-hint中做匹配
        //     CodeMirror.ukeys = words;
        //     //调用显示提示
        //     editor.showHint();
        // });

        // _this.sqlEditor = sqlEditor;
        // let pageMenus = [];
        // pageMenus.push($.query.string('logicTable'));
        // pageMenus.push($.query.string('actualTable'));
        // _this.page.menus = pageMenus;
        _this.loadTables('dataSource');
        _this.execute();
    },
    methods: {
        loadTables: function (datasource) {
            let _this = this;
            $.http.get({
                url: _this.host + '/api/jdbc/menus',
                data: {
                    dataSource: datasource
                },
                callback: function (result) {
                    let menus = [];

                    for (let i in result.data) {
                        let table = result.data[i];

                        let submenus = [];

                        for (let i in table.actualTables) {
                            let actualTable = table.actualTables[i];
                            submenus.push({
                                id: actualTable,
                                name: actualTable,
                                // link: 'javascript:void(0);',
                                link: 'jdbc?logicTable=' + table.logicTable + '&actualTable=' + actualTable,
                                mini: actualTable.replace(table.logicTable + '_', '')
                            })
                        }


                        let menu = {
                            id: table.logicTable,
                            name: table.logicTable,
                            icon: 'table_chart',
                            // link: 'request-mapping?method=' + pattern.method + '&pattern=' + pattern.pattern + '&host=' + _this.request.host,
                            link: 'javascript:void(0);',
                            badge: table.actualTables.length,
                            badgeClass: 'badge-success',
                            menus: submenus

                        }


                        menus.push(menu)

                    }


                    _this.sidebar.menus = menus;
                }
            })
        },
        formatDate: function (format, date) {
            if (date == null) return '';
            return new Date(date).format(format);
        },
        execute: function () {
            let _this = this;
            // let sql = _this.sqlEditor.getValue();
            let sql = 'select * from person';
            _this.loggers.push("-->  " + sql);
            $.http.post({
                url: _this.host + '/api/jdbc/execute',
                data: {
                    sql: sql
                },
                callback: function (result) {

                    if (result.success) {
                        _this.resultSet = result.data[0];
                        $('#tables').BootstrapTable.refresh();
                        // alert($('#tables').bootstrapTable('getOptions').totalRows);
                    } else {
                        swal({
                            title: 'SQL Exception',
                            text: result.message,
                            type: 'warning',
                            timer: 2000,
                            confirmButtonText: 'I Known it',
                            confirmButtonClass: "btn btn-success",
                            buttonsStyling: false
                        })
                    }


                }
            })
        }
    }
});