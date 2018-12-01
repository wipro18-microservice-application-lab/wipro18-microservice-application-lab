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
        }
    });
}

function articleGetAllCommand() {
    $.get(WAREHOUSE_URL , function(response) {
        response.forEach(function(articleDto) {
            $('#all-articles').find('> tbody:last-child')
                .append(
                    '<tr>' +
                    `<td>${articleDto.articleId}</td>` +
                    `<td>${articleDto.name}</td>` +
                    `<td>${articleDto.description}</td>` +
                    `<td>${articleDto.price}</td>` +
                    `<td>${articleDto.quantity}</td>` +
                    '</tr>');
        });
    });
}

function articleGetByIdCommand(articleId) {
    $.get(WAREHOUSE_URL + '/articles/' + articleId, function(articleDto) {
        $('#article-by-id').find('> tbody:last-child')
            .append(
                '<tr>' +
                `<td>${articleDto.articleId}</td>` +
                `<td>${articleDto.name}</td>` +
                `<td>${articleDto.description}</td>` +
                `<td>${articleDto.price}</td>` +
                `<td>${articleDto.quantity}</td>` +
                '</tr>');
    });
}