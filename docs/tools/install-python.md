Python is an extremely flexable language. There are many ways one can install and
configure it. However, with Data Analytics, it's hard to beat [Anaconda Python][]
as the platform was designed specifically for this type of work. You are more than
welcome to use whatever methods suits your work environment best.

The [Anaconda Python Install Instruction](https://docs.anaconda.com/anaconda/install/linux/)
for Linux are quite good, so there's no need to reiterate their process.
However, I will go over the steps I use for an example.

## Install Prerequisites

This step is recommended. If you plan on using any of the visual UX tools, ensure you
install the [Prerequisites](https://docs.anaconda.com/anaconda/install/linux/#prerequisites).


## Begin Installation

After installing the prerequisites, perform the following tasks in the same terminal.

>NOTE: Check for the latest version at the bottom on the
>[Install Page](https://www.anaconda.com/products/individual#linux). 
>At the time of this writing, it was `Anancond3-2020-11`. Change the curl link
>below as needed. Any resent version should suffice.

```bash
# change directories to download
cd ~/Downloads

# Ensure you have curl installed
sudo apt update && sudo apt install curl -y

# download the file
curl https://repo.anaconda.com/archive/Anaconda3-2020.11-Linux-x86_64.sh

# once downloaded, run the installer
bash ~/Downloads/Anaconda3-2020.11-Linux-x86_64.sh

# Install Notes:
#   - Accept the Defaults = yes
#   - Add Anaconda to your path = yes
#   - Close then re-open a terminal
```

## Post Installation

Upon reopening the terminal, you should see `(base)` in front of your path.
If you don not, you can activate it with the following.

```bash
# activate base, this will auto-activate base each time you open a terminal
conda config --set auto_activate_base True

# source bashrc file
source ~/.bashrc

# check Python version
python -V

# check path to Python
which python

# the path to Python base should point to
/home/$USER/anaconda3/bin/python
```

## Create Virtual Environment

You can create as many or few virtual environments as you wish.
Generally, I create one per project, sometimes more depending on
complexity and test requirements.

You can name the virtual environments anything you like. I tend
to name it after the project, in this case `wsprana`.

Additionally, I normally stay one minor version back from the latest 
[Mainstream Python](https://www.python.org/downloads/) release
to help with package compatibility. At the time of this writing it
was `3.9.1`. Therefore, we'll create a `v3.8` virtual environment.

In the terminal perform the following:

```bash
# create virtual environment
conda create -n wsprana -y python=3.8

# after the creation, activate the new venv
conda activate wsprana

# update pip, optional, but helps with warnings
python -m pip install --upgrade pip
```

>NOTE: Each time you close and re-open a terminal, you will be set back to
>the `(base)` environment (this is by design). You can change this behavior 
>by adding `conda activate wsprana` to your `~/.bashrc` file.

## Conclusion

This completes the basic virtual environment installation.
You should take a few moments and read through the
[Conda Documentation](https://docs.conda.io/projects/conda/en/latest/)
to become familiar with basic `conda usage`.

[Anaconda Python]: https://www.anaconda.com/