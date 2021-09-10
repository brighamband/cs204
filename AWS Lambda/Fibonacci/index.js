exports.handler = async (event) => {
    let n = event.queryStringParameters?.n;
    // IF PASSING IN INFO DIRECTLY -- let n = event.n;
    
    if (n === undefined) {
        return ({
            statusCode: 400,
            body: "Bad parameters.  Must specify a value for n."
        })
    }
    
    function fibonacci(num) {
        if (num <= 2) {
            return 1;
        }
        return fibonacci(num - 1) + fibonacci(num - 2);
    }
    
    const response = {
        statusCode: 200,
        body: JSON.stringify({
            "fibonacci": fibonacci(n),
            "author": "Brigham Andersen",
        })
    };
    return response;
};
