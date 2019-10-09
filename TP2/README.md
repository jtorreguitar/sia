# Joe, Tomás and Sofía's wonderful neural network

## Training/testing examples

The file terrain01.data contains the input columns with their output value to the right. The network takes in two input parameters under the labels x1 and x2 and uses the numbers under the column labelled z as their corresponding output. Thus, the network has strictly  two neurons as inputs and 1 neuron as an ouput, this will be important in the configuration stage. The terrain01.data file should look something like:

| x1  | x2  | z |
|----:|----:|--:|
| x11 | x21 | z1|
|  .  |  .  | . |
|  .  |  .  | . |
|  .  |  .  | . |
| x1n | x2n | zn|

## Configuring the network

The network has many different configurable parameters that can be changed through the config.data file. These are:

input_file: The file where the aforementioned examples are stored. Starts as terraino1.data

neurons: An array containing how many neurons will be in each layer, example: 2,10,1 represents a network with 2 input neurons, 1 hidden layer with 10 neurons and one output neuron *

epochs: The ammount of times the network will cycle through the examples

training_size: The amount of examples that will be used for training as opposed to testing. Of course, this number should be smaller than the total amount of examples

training_size_percentage: A number from 0 to 1 denoting the percentage of examples that will be used for training (it cannot be used at the same time as the training_size variable)

training_size_percentage_flag: If this is set to 1 the training_size_percentage variable will be used instead of training_size

eta: The learning rate, a multiplicative modifier to the updating of the weights during each backpropagation cycle

error_threshold_flag: A flag that denotes whether the network will stop execution when the error reaches a specified goal

momentum_flag: Whether momentum will be used as a learning mechanism

alpha_momentum: Percentage variation when using momentum

adaptative_eta_flag: Whether the learning rate will be adaptative

eta_check_steps: 5

eta_increase_value: The factor by which will the learning rate will be increased

eta_decrease_factor: The factor by which will the learning rate will be decreased

error_threshold_value: When the error reaches this value execution will stop

epsilon_error: If the difference between the network's output and the actual value is less than this the output will be considered correct

batch: The size of the batch, if you don't want to use batch set this value to 1

tanh: Whether the network will use tanh as activation function or the sigmoid

saturation_prevention: How much the network will stop saturation

\* Note that given the design of the network the first layer must always have 2 neurons and the last must always have 1.
