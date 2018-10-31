from flask import Flask, jsonify
from flask_restful import Api, Resource

from discovery_server.common.parse_json import get_connection_info


class Discovery(Resource):

    def get(self, name):
        exchanges, queues = get_connection_info(name)
        return jsonify(exchanges=exchanges, queues=queues)


app = Flask(__name__)
api = Api(app)
api.add_resource(Discovery, '/<string:name>')


if __name__ == '__main__':
    app.run(debug=True)
