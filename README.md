# BladeTurbineGA

My friend Landon who is a Mechanical Engineering Major (with an Aerospace specialization) came to me asking for my help for a project he was working on. He was building a fixed-wing drone with VTOL capabilites, and needed to know the best parameters for his electric turbine engine such as diameter, the number of blades, and the angles of the blades at the root and at the tip to produce the maximum amount of thrust without exceeding a certain amount of torque. He knew of my expertise with genetic algorithms and asked if it was possible to help, and of course I did.

We called over Discord and shared our screens and went to work. I described to him the process by which the algorithm works, and he walked me through how to perform all the math for calculating the thrust and torque given our parameters. The code got a lil messy in some places, but I cleaned it up quite a bit, and he has his own notes and he says he understands it, so thats good enough for me I suppose.

The algorithm seemed to confirm some fairly obvious stuff such as maximizing the diameter and the size and number of blades will produce the most thrust, however the angles at the tip and end of the blades varied quite a bit depending on the other inputs. Landon wanted to adjust the range of the inputs quite a bit as he realized some of his initial range wasn't practical, which was very to switch out since the code was structured neatly and range of the parameters was easily set in a "Limits" class file.

His initial goal was 2.5 lbs of force, and the algorithm was able to find a set of parameters that produced 2.99 lbs of force. Overall the algorithm was a huge success, he can easily switch out the parameters on his own to play around with.
