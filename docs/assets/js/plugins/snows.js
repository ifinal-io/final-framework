(function ($) {
  $.fn.snow = function (options) {
    var $flake = $('<div id="snowbox" />').css({'position': 'absolute', 'z-index': '9999', 'top': '-50px'}).html('❄'),
        documentHeight = $(document).height(),
        documentWidth = $(document).width(),
        defaults = {
          minSize: 5,
          maxSize: 25,
          newOn: 500,
          flakeColor: getRandomColor() /* 此处可以定义雪花颜色，若要白色可以改为#FFFFFF */
        },
        options = $.extend({}, defaults, options);
    var interval = setInterval(function () {
      var startPositionLeft = Math.random() * documentWidth - 100,
          startOpacity = 0.5 + Math.random(),
          sizeFlake = options.minSize + Math.random() * options.maxSize,
          endPositionTop = documentHeight - 200,
          endPositionLeft = startPositionLeft - 500 + Math.random() * 500,
          durationFall = documentHeight * 10 + Math.random() * 5000;
      $flake.clone().appendTo('body').css({
        left: startPositionLeft,
        opacity: startOpacity,
        'font-size': sizeFlake,
        color: getRandomColor()
      }).animate({
        top: endPositionTop,
        left: endPositionLeft,
        opacity: 0.2
      }, durationFall, 'linear', function () {
        $(this).remove()
      });
    }, options.newOn);
  };
})(jQuery);