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
        timeout: TIMEOUT,
        success: function (data) {
            let row = '';
            if (data.result === 'ORDER SUCCESSFUL') {
                row = '<td style="background-color: #C8E6C9">' + `${data.result.toLowerCase()}</td>`
            } else {
                row = '<td style="background-color: #FFCDD2">' + `${data.result.toLowerCase()}</td>`
            }

            $('#orderCreateResults').find('> tbody:last-child')
                .append(
                    '<tr>' +
                    row +
                    '</tr>');
        },
        error: function () {
            $('#orderCreateResults').find('> tbody:last-child')
                .append(
                    '<tr>' +
                    '<td style="background-color: #FFCDD2">gateway connection error</td>' +
                    '</tr>');
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
        timeout: TIMEOUT,
        success: function (data) {
            let row = '';
            if (data.result === 'successful') {
                row = '<td style="background-color: #C8E6C9">' + `${data.result.toLowerCase()}</td>`
            } else {
                row = '<td style="background-color: #FFCDD2">' + `${data.result.toLowerCase()}</td>`
            }

            $('#orderUpdateStatusResults').find('> tbody:last-child')
                .append(
                    '<tr>' +
                    row +
                    '</tr>');
        },
        error: function () {
            $('#orderUpdateStatusResults').find('> tbody:last-child')
                .append(
                    '<tr>' +
                    '<td style="background-color: #FFCDD2">connection error</td>' +
                    '</tr>');
        }
    });
}

function orderGetAllCommand() {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: SALES_URL,
        contentType: "application/json; charset=utf-8",
        traditional: true,
        timeout: TIMEOUT,
        success: function (response) {
            response.forEach(function(orderDto) {
                $('#all-orders').find('> tbody:last-child')
                    .append(
                        '<tr>' +
                        `<td>${orderDto.orderId}</td>` +
                        `<td id='customer${orderDto.orderId}'>${orderDto.customerId}</td>` +
                        `<td id='item${orderDto.orderId}'>${JSON.stringify(orderDto.amountToArticle)}</td>` +
                        `<td>${orderDto.totalPrice}</td>` +
                        `<td>${orderDto.status}</td>` +
                        '</tr>');
            });
        },
        error: function () {
            $('#all-orders').find('> tbody:last-child')
                .append(
                    '<tr>' +
                    '<td style="background-color: #FFCDD2">gateway connection error</td>' +
                    '<td style="background-color: #FFCDD2"></td>' +
                    '<td style="background-color: #FFCDD2"></td>' +
                    '<td style="background-color: #FFCDD2"></td>' +
                    '<td style="background-color: #FFCDD2"></td>' +
                    '</tr>');
        }
    });
}

function orderGetAllCommandForCreate() {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: WAREHOUSE_URL,
        contentType: "application/json; charset=utf-8",
        traditional: true,
        timeout: TIMEOUT,
        success: function (response) {
            response.forEach(function(articleDto) {
                $('#article-for-create').find('> tbody:last-child')
                    .append(
                        '<tr>' +
                        `<td>${articleDto.name}</td>` +
                        `<td>${articleDto.description}</td>` +
                        `<td>${articleDto.price}</td>` +
                        `<td><button class="mui-btn mui-btn--flat mui-btn--primary" 
                                onclick="addArticle(${articleDto.articleId}, ${articleDto.price})">Add</button></td>` +
                        '</tr>');
            });
        },
        error: function () {
            $('#article-for-create').find('> tbody:last-child')
                .append(
                    '<tr>' +
                    '<td style="background-color: #FFCDD2">gateway connection error</td>' +
                    '<td style="background-color: #FFCDD2"></td>' +
                    '<td style="background-color: #FFCDD2"></td>' +
                    '<td style="background-color: #FFCDD2"></td>' +
                    '</tr>');
        }
    });
}

function orderGetAllByCustomerIdCommand(customerId) {
    $.ajax({
        headers: {
            'Accept': 'application/json'
        },
        type: 'GET',
        url: SALES_URL + '/customer/' + customerId,
        traditional: true,
        timeout: TIMEOUT,
        success: function (response) {
            response.forEach(function(orderDto) {
                $('#all-customer-orders').find('> tbody:last-child')
                    .append(
                        '<tr>' +
                        `<td>${orderDto.orderId}</td>` +
                        `<td id='customer${orderDto.orderId}'>${orderDto.customerId}</td>` +
                        `<td id='item${orderDto.orderId}'>${JSON.stringify(orderDto.amountToArticle)}</td>` +
                        `<td>${orderDto.totalPrice}</td>`+
                        `<td>${orderDto.status}</td>` +
                        '</tr>');
            });
        },
        error: function () {
            $('#all-customer-orders').find('> tbody:last-child')
                .append(
                    '<tr>' +
                    '<td style="background-color: #FFCDD2">gateway connection error</td>' +
                    '<td style="background-color: #FFCDD2"></td>' +
                    '<td style="background-color: #FFCDD2"></td>' +
                    '<td style="background-color: #FFCDD2"></td>' +
                    '<td style="background-color: #FFCDD2"></td>' +
                    '</tr>');
        }
    });
}