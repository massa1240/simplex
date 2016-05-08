from flask import Flask, Response
from subprocess import *
from flask import render_template
app = Flask(__name__)

def jarWrapper(*args):
    process = Popen(['java', '-jar']+list(args), stdout=PIPE, stderr=PIPE)
    ret = []
    while process.poll() is None:
        line = process.stdout.readline()
        ret.append(line)
    stdout, stderr = process.communicate()

    return ret

@app.route("/")
def index():
	return render_template('index.html')

@app.route("/simplex", methods=['POST'])
def simplex():
	args = ['simplex.jar']
	result = jarWrapper(*args)
	resp = Response(response=result[0],
	    status=200,
	    mimetype="application/json")
	return resp

if __name__ == "__main__":
    app.run(debug=True)