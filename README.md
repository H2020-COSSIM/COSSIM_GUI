# COSSIM-GUI

This repository includes the Eclipse-based GUI which is implemented in the context of COSSIM. Specifically, the Eclipse-based GUI of OMNeT++ has been extended integrate the capabilities of the COSSIM framework. Our GUI consists of two Eclipse plugins: (i) the simulation configuration tool and (ii) the execution monitoring tool.

## The simulation configuration tool

The simulation configuration tool has the form of a wizard which is installed as a plugin in Eclipse/OMNeT++ and guides the user through the cGEM5 configuration process for each of the simulated nodes. Typically this process is performed through the command line and may require a large number of parameters to be set for each of the nodes. For the general case scenario, the GUI offers typical configuration options and can significantly speed up the process of preparing a simulation run. For more complex scenarios, where a user requires a series of options not available within the GUI, the GUI provides the opportunity to edit directly all command options. For inexperienced users (and supposing the required system settings allow for this), we highly recommend the use of the GUI as besides providing an easier way to instrument the simulation, it prevents the setting of wrong parameters, thus minimizing the risk of starting a time-consuming simulation that will latterly be proven wrong or inadequate. 

## The execution monitoring tool

The second plugin that has been developed is the COSSIM execution monitoring tool which is a graphical interface that integrates and visualizes the most important cGEM5 and cMcPat results. The output results that the monitoring tool displays, can be presented either per node or per simulated parallel system. That means that the tool can show the results of each node separately or of all of them together.

## Compiling the COSSIM-GUI

Please read the [Compilation](src/GuiCompilation.pdf) file.

## Using COSSIM-GUI in the context of the COSSIM simulation framework

Please refer to [COSSIM _framework](https://github.com/H2020-COSSIM/COSSIM_framework) repository for all required instructions.

## Licensing

Refer to the [LICENSE](LICENSE) file included.

#### Authors

* Stamatis Andrianakis (sandrian@gmail.com)

Please contact for any questions.

## Acknowledgments

Code developed for the H2020-COSSIM project.

