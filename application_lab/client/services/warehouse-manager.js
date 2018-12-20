function checkArticleQuantityCommand(articleCheckQuantityDto) {

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'PUT',
        url: WAREHOUSE_URL + '/quantity',
        data: JSON.stringify(articleCheckQuantityDto),
        contentType: "application/json; charset=utf-8",
        traditional: true,
        timeout: TIMEOUT,
        success: function (data) {
            let row = '';
            if (data.result === 'enough articles in stock') {
                row = '<td style="background-color: #C8E6C9">' + `${data.result.toLowerCase()}</td>`
            } else {
                row = '<td style="background-color: #FFCDD2">' + `${data.result.toLowerCase()}</td>`
            }

            $('#checkQuantityResults').find('> tbody:last-child')
                .append(
                    '<tr>' +
                        row +
                    '</tr>');
        },
        error: function () {
            $('#checkQuantityResults').find('> tbody:last-child')
                .append(
                    '<tr>' +
                        '<td style="background-color: #FFCDD2">gateway connection error</td>' +
                    '</tr>');
        }
    });
}

function articleGetAllCommand() {
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
                let articlePrice = parseFloat(Math.round(articleDto.price * 100) / 100).toFixed(2);

                $('#all-articles').find('> tbody:last-child')
                    .append(
                        '<tr>' +
                            `<td>${articleDto.articleId}</td>` +
                            `<td>${articleDto.name}</td>` +
                            `<td>${articleDto.description}</td>` +
                            `<td>${articlePrice}</td>` +
                            `<td>${articleDto.quantity}</td>` +
                        '</tr>');
            });
        },
        error: function () {
            $('#all-articles').find('> tbody:last-child')
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

function articleGetByIdCommand(articleId) {
    $.ajax({
        headers: {
            'Accept': 'application/json'
        },
        type: 'GET',
        url: WAREHOUSE_URL + '/articles/' + articleId,
        traditional: true,
        timeout: TIMEOUT,
        success: function (articleDto) {
            let articlePrice = parseFloat(Math.round(articleDto.price * 100) / 100).toFixed(2);

            $('#article-by-id').find('> tbody:last-child')
                .append(
                    '<tr>' +
                        `<td>${articleDto.articleId}</td>` +
                        `<td>${articleDto.name}</td>` +
                        `<td>${articleDto.description}</td>` +
                        `<td>${articlePrice}</td>` +
                        `<td>${articleDto.quantity}</td>` +
                    '</tr>');
        },
        error: function () {
            $('#article-by-id').find('> tbody:last-child')
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

function setArticleNameById(id, articleId, articleQuantity) {
    $.get(WAREHOUSE_URL + '/articles/' + articleId, function(articleDto) {
        $('#' + id).find('> ul').append('<li>name: ' + articleDto.name + ', quantity: ' + articleQuantity + '</li>');
    });
}