function createOrder(orderDto) {
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
