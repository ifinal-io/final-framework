// https://github.com/ghiculescu/jekyll-table-of-contents
(function ($) {
    $.fn.toc = function (options) {
        const $this = this;

        let defaults = {
                noBackToTopLinks: false,
                title: '',
                minimumHeaders: 1,
                headers: 'h1, h2, h3, h4, h5, h6',
                listType: 'ul', // values: [ol|ul]
                showEffect: 'show', // values: [show|slideDown|fadeIn|none]
                showSpeed: 'slow', // set to 0 to deactivate effect
                scrollMarginTop: 100,
                scrollDuration: 300,
                classes: {
                    list: '',
                    item: ''
                }
            },
            settings = $.extend(defaults, options);

        function fixedEncodeURIComponent(str) {
            return encodeURIComponent(str).replace(/[!'()*]/g, function (c) {
                return '%' + c.charCodeAt(0).toString(16);
            });
        }

        function createLink(header) {
            var innerText = (header.textContent === undefined) ? header.innerText : header.textContent;
            // return "<a class='nav-link' href='#" + fixedEncodeURIComponent(header.id) + "'>" + innerText + "</a>";
            return "<a class='nav-link' href='#" + header.id + "'>" + innerText + "</a>";
        }

        var headers = $(settings.headers).filter(function () {
            // get all headers with an ID
            var previousSiblingName = $(this).prev().attr("name");
            if (!this.id && previousSiblingName) {
                this.id = $(this).attr("id", previousSiblingName.replace(/\./g, "-"));
            }
            return this.id;
        }), output = $(this);
        if (!headers.length || headers.length < settings.minimumHeaders || !output.length) {
            $(this).hide();
            return;
        }

        if (0 === settings.showSpeed) {
            settings.showEffect = 'none';
        }

        var render = {
            show: function () {
                output.hide().html(html).show(settings.showSpeed);
            },
            slideDown: function () {
                output.hide().html(html).slideDown(settings.showSpeed);
            },
            fadeIn: function () {
                output.hide().html(html).fadeIn(settings.showSpeed);
            },
            none: function () {
                output.html(html);
            }
        };

        var get_level = function (ele) {
            return parseInt(ele.nodeName.replace("H", ""), 10);
        };
        var highest_level = headers.map(function (_, ele) {
            return get_level(ele);
        }).get().sort()[0];
        var return_to_top = '<i class="icon-arrow-up back-to-top"> </i>';

        var level = get_level(headers[0]),
            this_level,
            html = settings.title + " <" + settings.listType + " class=\"section-nav nav flex-column" + settings.classes.list + "\">";
        headers.on('click', function () {
            if (!settings.noBackToTopLinks) {
                window.location.hash = this.id;
            }
        })
            .addClass('clickable-header')
            .each(function (_, header) {
                this_level = get_level(header);
                if (!settings.noBackToTopLinks && this_level === highest_level) {
                    $(header).addClass('top-level-header').after(return_to_top);
                }
                if (this_level === level) // same level as before; same indenting
                    html += "<li class=\"" + settings.classes.item + "\">" + createLink(header);
                else if (this_level <= level) { // higher level than before; end parent ol
                    for (var i = this_level; i < level; i++) {
                        html += "</li></" + settings.listType + ">"
                    }
                    html += "<li class=\"" + settings.classes.item + "\">" + createLink(header);
                } else if (this_level > level) { // lower level than before; expand the previous to contain a ol
                    for (i = this_level; i > level; i--) {

                        html += "<" + settings.listType + " class=\"" + settings.classes.list + "\">" +
                            "<li class=\"" + settings.classes.item + "\">"
                    }
                    html += createLink(header);
                }
                level = this_level; // update for the next one
            });
        html += "</" + settings.listType + ">";
        if (!settings.noBackToTopLinks) {
            $(document).on('click', '.back-to-top', function () {
                $(window).scrollTop(0);
                window.location.hash = '';
            });
        }

        render[settings.showEffect]();

        $this.find('a').on('click', function () {
            $('html,body').animate({scrollTop: ($($(this).attr('href')).offset().top - settings.scrollMarginTop)}, settings.scrollDuration);
        });


    };
})(jQuery);