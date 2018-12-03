function customerCreateCommand(customerDto) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: CUSTOMER_URL,
        data: JSON.stringify(customerDto),
        contentType: "application/json; charset=utf-8",
        traditional: true,
        success: function (data) {

            let row = '';
            if (data.result.toLowerCase() === 'successful') {
                row = '<td style="background-color: #C8E6C9">' + `${data.result.toLowerCase()}</td>`
            } else {
                row = '<td style="background-color: #FFCDD2">' + `${data.result.toLowerCase()}</td>`
            }

            $('#customerCreateResults').find('> tbody:last-child')
                .append(
                    '<tr>' +
                    row +
                    '</tr>');
        }
    });
}

function customerGetAllCommand() {
    $.get(CUSTOMER_URL , function(response) {
        response.forEach(function(customerDto) {
            $('#all-customers').find('> tbody:last-child')
                .append(
                    '<tr>' +
                    `<td>${customerDto.customerId}</td>` +
                    `<td>${customerDto.fullName}</td>` +
                    `<td>${customerDto.address}</td>` +
                    `<td>${customerDto.email}</td>` +
                    `<td>${customerDto.isFlagged}</td>` +
                    '</tr>');
        });
    });
}

function customerGetByIdCommand(customerId) {
    $.get(CUSTOMER_URL + '/' + customerId, function(customerDto) {
        $('#customers-by-id').find('> tbody:last-child')
            .append(
                '<tr>' +
                `<td>${customerDto.customerId}</td>` +
                `<td>${customerDto.fullName}</td>` +
                `<td>${customerDto.address}</td>` +
                `<td>${customerDto.email}</td>` +
                `<td>${customerDto.isFlagged}</td>` +
                '</tr>');
    });
}