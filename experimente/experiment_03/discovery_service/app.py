from flask import Flask, request, jsonify
from flask_restful import Api, Resource, abort

register = {}


class Discovery(Resource):

    @staticmethod
    def get(domain):
        if domain in register:
            return jsonify(register[domain])
        else:
            return abort(404)

    @staticmethod
    def put(domain):
        register[domain] = request.get_json()

        return jsonify(domain)


app = Flask(__name__)
api = Api(app)
api.add_resource(Discovery, '/<string:domain>')

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
