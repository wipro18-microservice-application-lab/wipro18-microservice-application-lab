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
        timeout: TIMEOUT,
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
        },
        error: function () {
            $('#customerCreateResults').find('> tbody:last-child')
                .append(
                    '<tr>' +
                        '<td style="background-color: #FFCDD2">gateway connection error</td>' +
                    '</tr>');
        }
    });
}

function customerGetAllCommand() {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: CUSTOMER_URL,
        contentType: "application/json; charset=utf-8",
        traditional: true,
        timeout: TIMEOUT,
        success: function (response) {
            response.forEach(function(customerDto) {
                let flaggedCell = '';
                if (customerDto.isFlagged) {
                    flaggedCell = `<td style="background-color: #FFCDD2">${customerDto.isFlagged}</td>`;
                } else {
                    flaggedCell = `<td style="background-color: #C8E6C9">${customerDto.isFlagged}</td>`;
                }

                $('#all-customers').find('> tbody:last-child')
                    .append(
                        '<tr>' +
                        `<td>${customerDto.customerId}</td>` +
                        `<td>${customerDto.fullName}</td>` +
                        `<td>${customerDto.address}</td>` +
                        `<td>${customerDto.email}</td>` +
                        flaggedCell +
                        '</tr>');
            });
        },
        error: function () {
            $('#all-customers').find('> tbody:last-child')
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

function customerGetByIdCommand(customerId) {
    $.ajax({
        headers: {
            'Accept': 'application/json'
        },
        type: 'GET',
        url: CUSTOMER_URL + '/' + customerId,
        traditional: true,
        timeout: TIMEOUT,
        success: function (customerDto) {
            let flaggedCell = '';
            if (customerDto.isFlagged) {
                flaggedCell = `<td style="background-color: #FFCDD2">${customerDto.isFlagged}</td>`;
            } else {
                flaggedCell = `<td style="background-color: #C8E6C9">${customerDto.isFlagged}</td>`;
            }

            $('#customers-by-id').find('> tbody:last-child')
                .append(
                    '<tr>' +
                        `<td>${customerDto.customerId}</td>` +
                        `<td>${customerDto.fullName}</td>` +
                        `<td>${customerDto.address}</td>` +
                        `<td>${customerDto.email}</td>` +
                        `${flaggedCell}` +
                    '</tr>');
        },
        error: function () {
            $('#customers-by-id').find('> tbody:last-child')
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

function setCustomerNameById(id, customerId) {
    $.get(CUSTOMER_URL + '/' + customerId, function(customerDto) {
        $('#' + id).text(customerDto.fullName);
    });
}