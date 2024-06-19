# import pickle
# import numpy as np
# from sklearn.ensemble import RandomForestClassifier
# from sklearn.model_selection import train_test_split
# from sklearn.metrics import accuracy_score
#
# # Load the data
# data_dict = pickle.load(open('./data.pickle', 'rb'))
#
# # Convert all elements to numpy arrays
# data_dict['data'] = [np.array(d) for d in data_dict['data']]
#
# # Inspect the shapes of elements in data_dict['data']
# data_shapes = [d.shape for d in data_dict['data']]
# print("Data shapes:", data_shapes)
#
# # Find the maximum shape
# max_shape = tuple(map(max, zip(*data_shapes)))
#
# # Handle inconsistent shapes (example approach)
# # Pad or truncate data to have the same shape
# def pad_to_shape(arr, shape):
#     # Create an array of the target shape filled with zeros
#     result = np.zeros(shape)
#     # Fill the result array with the original data
#     slices = tuple(slice(0, min(dim, shape[i])) for i, dim in enumerate(arr.shape))
#     result[slices] = arr[slices]
#     return result
#
# # Ensure all data have the same shape
# uniform_data = np.array([pad_to_shape(d, max_shape) for d in data_dict['data']])
#
# # Convert labels to NumPy array
# labels = np.asarray(data_dict['labels'])
#
# # Flatten the data if needed (e.g., if the classifier expects 2D data)
# uniform_data_flattened = uniform_data.reshape(uniform_data.shape[0], -1)
#
# # Split the data
# x_train, x_test, y_train, y_test = train_test_split(uniform_data_flattened, labels, test_size=0.2, shuffle=True, stratify=labels)
#
# # Initialize and train the model
# model = RandomForestClassifier()
# model.fit(x_train, y_train)
#
# # Make predictions and evaluate the model
# y_predict = model.predict(x_test)
# score = accuracy_score(y_predict, y_test)
# print('{}% of samples were classified correctly !'.format(score * 100))
#
# # Save the model
# with open('model.p', 'wb') as f:
#     pickle.dump({'model': model}, f)

import pickle
import numpy as np
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

# Load the data
data_dict = pickle.load(open('./data.pickle', 'rb'))

# Convert all elements to numpy arrays
data_dict['data'] = [np.array(d) for d in data_dict['data']]

# Ensure all data have the same shape
max_shape = max(d.shape[0] for d in data_dict['data'])
uniform_data = np.array([np.pad(d, (0, max_shape - len(d)), 'constant') for d in data_dict['data']])

# Convert labels to NumPy array
labels = np.asarray(data_dict['labels'])

# Split the data
x_train, x_test, y_train, y_test = train_test_split(uniform_data, labels, test_size=0.2, shuffle=True, stratify=labels)

# Initialize and train the model
model = RandomForestClassifier()
model.fit(x_train, y_train)

# Make predictions and evaluate the model
y_predict = model.predict(x_test)
score = accuracy_score(y_predict, y_test)
print('{}% of samples were classified correctly !'.format(score * 100))

# Save the model
with open('model.p', 'wb') as f:
    pickle.dump({'model': model}, f)
