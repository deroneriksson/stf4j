

## CIFAR-10 SavedModel Information

To train a CIFAR-10 model and save as SavedModel.

```
$ pwd
/models/official/resnet

$ python3 cifar10_main.py --export_dir /tmp/cifar10_saved_model
```


## MNIST SavedModel Information

To train an MNIST model and save as SavedModel.

```
$ pwd
/models/official/mnist/

$ python3 mnist.py --export_dir /tmp/mnist_saved_model
```


## Higgs Boosted Trees SavedModel Information

To train a Higgs Boosted Trees model and save as SavedModel.

```
$ pwd
/models/official/boosted_trees

$ export PYTHONPATH=/models/

$ python3 data_download.py

$ python3 train_higgs.py --export_dir /higgs_boosted_trees_saved_model
```

