function reminderGetAllCommand() {
    console.log("hi");

    $.get(REMINDER_URL , function(response) {
        console.log(response.reminders);

        response.reminder.forEach(function(reminderDto) {
            $('#all-reminders').find('> tbody:last-child')
                .append(
                    '<tr>' +
                        `<td>${reminderDto.customerId}</td>` +
                        `<td>${reminderDto.fee}</td>` +
                        `<td>${reminderDto.issueDate}</td>` +
                    '</tr>');
        });
    });
}