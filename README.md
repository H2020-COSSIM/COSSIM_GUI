# COSSIM-GUI

This repository includes the Eclipse-based GUI which is implemented in the context of COSSIM. Specifically, the Eclipse-based GUI of OMNeT++ has been extended so as to fully integrate the capabilities of the COSSIM tool. Our GUI consists of two Eclipse plugins: (i) the simulation configuration tool and (ii) the execution monitoring tool.

## The simulation configuration tool

The simulation configuration tool has the form of a wizard which is installed as a plugin in Eclipse/OMNeT++
and guides the user through the GEM5 configuration process for each of the simulated nodes. This process is a very
time-consuming one as it is usually performed through the command line and needs a large number of parameters to
be set for each of the nodes. Based on our initial measurements within the COSSIM design team, the GUI, by itself,
reduces the configuration time of the simulation by 90% in the case of a 10 node system. In addition, our GUI prevents
the user from setting wrong parameters and thus minimizing the risk of starting a time-consuming simulation that
will latterly be proven wrong or inadequate.

## The execution monitoring tool

The second plugin that has been developed is the COSSIM execution monitoring tool which is a graphical interface
that integrates and visualize the most important cGEM5 and McPat results. The output results that the monitoring tool
displays, can be presented either per node or per simulated parallel system. That means that the tool can show the
results of each node separately or of all of them together.

## Compiling the COSSIM-GUI

Please read the [Installation](src/GuiInstallation.pdf) file.

## Using COSSIM-GUI in the context of the COSSIM simulation framework

Please refer to [COSSIM _framework](https://github.com/H2020-COSSIM/COSSIM_framework) repository for all required instructions.

## Licensing

Refer to the [LICENSE](LICENSE) file included.

#### Authors

* Stamatis Andrianakis (sandrian@gmail.com)

Please contact for any questions.

## Acknowledgments

Code developed for the H2020-COSSIM project.

