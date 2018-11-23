# Salesmanagement
| Routing Key / Topic              | Inputdaten                                                                        | Outputdaten                          |
| :------------------------------: | :-------------------------------------------------------------------------------: | :----------------------------------: |
| Order.command.create             | {"customerId":Integer,"amountToArticle":Map,"totalPrice":Double, "Status":String} | {"orderId":Integer, "result":String} |
| Order.command.getAll             | {}                                                                                | {"orders":List}                      |
| Order.command.getAllByCustomerId | {"customerId":Integer}                                                            | {"orders":List}                      |
| Order.command.updateStatus       | {"orderId": Integer, "Status":String}                                             | {"result":String}                    |