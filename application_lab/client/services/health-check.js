function checkHealth(target, url) {
    $.get(url , function(response) {
        $('#' + target).css('background-color', '#C8E6C9').html(response);
    })
        .fail(function() {
            $('#' + target).css('background-color', '#FFCDD2').html('sales management is not running..');
        });
}