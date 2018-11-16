function loadInventory() {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: SALES_URL,
        data: JSON.stringify({"customerId":11,"amountToArticle":{},"totalPrice":30.0}),
        contentType: "application/json; charset=utf-8",
        traditional: true,
        success: function (data) {
            console.log(data);
        }
    });
}
