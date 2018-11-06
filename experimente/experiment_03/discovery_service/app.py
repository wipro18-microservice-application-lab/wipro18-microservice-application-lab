from flask import Flask, jsonify
from flask_restful import Api, Resource

from parse_json import get_connection_info


class Discovery(Resource):

    def get(self, name):
        exchange, queues = get_connection_info(name)
        return jsonify(exchange=exchange, queues=queues)

    def put(self, name):
        request.form['data']



app = Flask(__name__)
api = Api(app)
api.add_resource(Discovery, '/<string:name>')


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
