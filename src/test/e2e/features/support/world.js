module.exports = function () {

    this.World = function World(callback) {
        this.prop = "Hello from the World!";

        this.greetings = function (name, callback) {
            console.log("\n----Hello " + name);
            callback();
        };

        callback();
    }
};
