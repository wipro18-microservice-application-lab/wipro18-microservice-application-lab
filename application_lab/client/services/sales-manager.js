function orderCreateCommand(orderDto) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: SALES_URL,
        data: JSON.stringify(orderDto),
        contentType: "application/json; charset=utf-8",
        traditional: true,
        success: function (data) {
            console.log(data);
        }
    });
}

function orderGetAllCommand() {
    $.get(SALES_URL , function(response) {
        response.forEach(function(order) {
            $('#all-orders').find('> tbody:last-child')
                .append('<tr>' +
                    `<td>${order.orderId}</td>` +
                    `<td>${order.customerId}</td>` +
                    `<td>${JSON.stringify(order.amountToArticle)}</td>` +
                    `<td>${order.totalPrice}</tr>`);
        });
    });
}

function orderGetAllByCustomerIdCommand(customerId) {
    $.get(SALES_URL + '/customer/' + customerId, function(response) {
        response.forEach(function(order) {
            $('#all-customer-orders').find('> tbody:last-child')
                .append('<tr>' +
                    `<td>${order.orderId}</td>` +
                    `<td>${order.customerId}</td>` +
                    `<td>${JSON.stringify(order.amountToArticle)}</td>` +
                    `<td>${order.totalPrice}</tr>`);
        });
    });
}