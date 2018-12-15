function reminderGetAllCommand() {
    $.get(REMINDER_URL , function(response) {
        let i = 0;

        response.reminders.forEach(function(reminderDto) {
            $('#all-reminders').find('> tbody:last-child')
                .append(
                    '<tr>' +
                        `<td id='reminder${i}'>${reminderDto.customerId}</td>` +
                        `<td>${reminderDto.fee}</td>` +
                        `<td>${reminderDto.issueDate}</td>` +
                    '</tr>');
            i++;
        });
    }).fail(function() {
        $('#all-reminders').find('> tbody:last-child')
            .append(
                '<tr>' +
                `<td style="background-color: #FFCDD2">gateway connection error</td>` +
                `<td style="background-color: #FFCDD2"></td>` +
                `<td style="background-color: #FFCDD2"></td>` +
                '</tr>');
    });
}