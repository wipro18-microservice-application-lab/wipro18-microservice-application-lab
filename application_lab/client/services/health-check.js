function checkHealth(target, url) {
    $.ajax({
        type: 'GET',
        url: url,
        contentType: "application/json; charset=utf-8",
        traditional: true,
        timeout: TIMEOUT,
        success: function (response) {
            $('#' + target).css('background-color', '#C8E6C9').html(response);
        },
        error: function () {
            $('#' + target).css('background-color', '#FFCDD2').html('gateway connection error');
        }
    });
}

function checkAllHealth() {
    let services = [SALES_HEALTH_URL, WAREHOUSE_HEALTH_URL, CUSTOMER_HEALTH_URL, REMINDER_HEALTH_URL];
    let target = 'overallHealth';

    services.forEach(url => {
        $.ajax({
            type: 'GET',
            url: url,
            contentType: "application/json; charset=utf-8",
            traditional: true,
            timeout: TIMEOUT,
            success: function (response) {
                $('#' + target).find('> tbody:last-child')
                    .append(
                        '<tr>' +
                        `<td>${url}</td>` +
                        `<td style="background-color: #C8E6C9">${response}</td>` +
                        '</tr>');
            },
            error: function () {
                $('#' + target).find('> tbody:last-child')
                    .append(
                        '<tr>' +
                        `<td>${url}</td>` +
                        `<td style="background-color: #FFCDD2">gateway connection error</td>` +
                        '</tr>');
            }
        });
    });
}