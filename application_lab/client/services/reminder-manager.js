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
    });
}