function checkArticleQuantityCommand(articleCheckQuantityDto) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: WAREHOUSE_URL,
        data: JSON.stringify(articleCheckQuantityDto),
        contentType: "application/json; charset=utf-8",
        traditional: true,
        success: function (data) {
            console.log(data);
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
    $.get(WAREHOUSE_URL + '/articles/' + articleId, function(response) {
        response.forEach(function(articleDto) {
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
    });
}