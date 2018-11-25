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

function orderUpdateStatusCommand(orderUpdateDto) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'PUT',
        url: SALES_URL,
        data: JSON.stringify(orderUpdateDto),
        contentType: "application/json; charset=utf-8",
        traditional: true,
        success: function (data) {
            console.log(data);
        }
    });
}

function orderGetAllCommand() {
    $.get(SALES_URL , function(response) {
        response.forEach(function(orderDto) {
            $('#all-orders').find('> tbody:last-child')
                .append(
                    '<tr>' +
                        `<td>${orderDto.orderId}</td>` +
                        `<td>${orderDto.customerId}</td>` +
                        `<td>${JSON.stringify(orderDto.amountToArticle)}</td>` +
                        `<td>${orderDto.totalPrice}</td>` +
                        `<td>${orderDto.status}</td>` +
                    '</tr>');
        });
    });
}

function orderGetAllByCustomerIdCommand(customerId) {
    $.get(SALES_URL + '/customer/' + customerId, function(response) {
        response.forEach(function(orderDto) {
            $('#all-customer-orders').find('> tbody:last-child')
                .append(
                    '<tr>' +
                        `<td>${orderDto.orderId}</td>` +
                        `<td>${orderDto.customerId}</td>` +
                        `<td>${JSON.stringify(orderDto.amountToArticle)}</td>` +
                        `<td>${orderDto.totalPrice}</td>`+
                        `<td>${orderDto.status}</td>` +
                    '</tr>');
        });
    });
}