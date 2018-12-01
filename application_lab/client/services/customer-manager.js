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
            console.log(data);
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
                '</tr>');
    });
}