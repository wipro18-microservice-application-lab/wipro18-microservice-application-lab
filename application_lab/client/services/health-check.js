function checkHealth(target, url) {
    $.get(url , function(response) {
        $('#' + target).css('background-color', '#C8E6C9').html(response);
    })
    .fail(function() {
        $('#' + target).css('background-color', '#FFCDD2').html('sales management is not running..');
    });
}

function checkAllHealth() {
    let services = [SALES_HEALTH_URL, WAREHOUSE_HEALTH_URL, CUSTOMER_HEALTH_URL, REMINDER_HEALTH_URL];
    let target = 'overallHealth';

    services.forEach(url => {
        $.get(url , function(response) {
            $('#' + target).find('> tbody:last-child')
                .append(
                    '<tr>' +
                    `<td>${url}</td>` +
                    `<td style="background-color: #C8E6C9">${response}</td>` +
                    '</tr>');
        })
        .fail(function() {
            $('#' + target).find('> tbody:last-child')
                .append(
                    '<tr>' +
                    `<td>${url}</td>` +
                    `<td style="background-color: #FFCDD2">${response}</td>` +
                    '</tr>');
        });
    });
}