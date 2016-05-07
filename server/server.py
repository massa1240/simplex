from flask import Flask
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

@app.route("/simplex")
def simplex():
	args = ['simplex.jar']
	result = jarWrapper(*args)
	return result[0]

if __name__ == "__main__":
    app.run(debug=True)