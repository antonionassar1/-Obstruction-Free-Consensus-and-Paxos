import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

plt.style.use('ggplot')

tle= [0.5,1,1.5,2]
N=[3,10,100]

latency_3_500_01 = np.mean((np.array([4,3,4,3,4])))
latency_3_1000_01 = np.mean(np.array([3,4,3,3,3]))
latency_3_1500_01 = np.mean(np.array([4,3,3,3,3]))
latency_3_2000_01 =np.mean(np.array([3,3,4,3,2]))

y3_01 = [latency_3_500_01, latency_3_1000_01,latency_3_1500_01,latency_3_2000_01]

latency_3_500_0 = np.mean(np.array([5,5,4,3,4]))
latency_3_1000_0 = np.mean(np.array([3,4,4,3,4]))
latency_3_1500_0 = np.mean(np.array([2,3,3,3,3]))
latency_3_2000_0 = np.mean(np.array([3,5,3,4,3]))

y3_0 = [latency_3_500_0, latency_3_1000_0, latency_3_1500_0, latency_3_2000_0]

latency_3_500_1 =np.mean(np.array([2,4,3,3,4]))
latency_3_1000_1 = np.mean(np.array([4,5,4,4,3]))
latency_3_1500_1 = np.mean(np.array([3,3,4,4,2]))
latency_3_2000_1 = np.mean(np.array([3,3,4,4,4]))

y3_1 = [latency_3_500_1, latency_3_1000_1, latency_3_1500_1, latency_3_2000_1]

##
latency_10_500_01 = np.mean(np.array([56,66,24,15,23]))
latency_10_1000_01 = np.mean(np.array([13,68,40,13,13]))
latency_10_1500_01 = np.mean(np.array([35,15,25,41,28]))
latency_10_2000_01 = np.mean(np.array([19,32,33,25,46]))

y10_01 = [latency_10_500_01, latency_10_1000_01, latency_10_1500_01, latency_10_2000_01 ]

latency_10_500_0 = np.mean(np.array([105,76,39,55,12]))
latency_10_1000_0 = np.mean(np.array([77,32,123,37,35]))
latency_10_1500_0 = np.mean(np.array([141,11,48,30,72]))
latency_10_2000_0 = np.mean(np.array([20,111,120,13,66]))

y10_0 = [latency_10_500_0, latency_10_1000_0, latency_10_1500_0, latency_10_2000_0]

latency_10_500_1 = np.mean(np.array([44,51,141,23,30]))
latency_10_1000_1 = np.mean(np.array([24,33,27,24,39]))
latency_10_1500_1 = np.mean(np.array([43,25,58,35,25]))
latency_10_2000_1 = np.mean(np.array([29,84,54,30,36]))

y10_1= [latency_10_500_1, latency_10_1000_1, latency_10_1500_1, latency_10_2000_1]

##

latency_100_500_01 = np.mean(np.array([577,518,528,557,520]))
latency_100_1000_01 = np.mean(np.array([1098,1015,1064,1054,1011]))
latency_100_1500_01 = np.mean(np.array([1562,1526,1541,1516,1552]))
latency_100_2000_01 = np.mean(np.array([2071,2032,2114,660,2023]))

y100_01=[latency_100_500_01, latency_100_1000_01, latency_100_1500_01, latency_100_2000_01]

latency_100_500_0 = np.mean(np.array([539,583,553,600,694]))
latency_100_1000_0 = np.mean(np.array([580,369,1154,1181,1130]))
latency_100_1500_0 = np.mean(np.array([862,366,601,465,1547]))
latency_100_2000_0 = np.mean(np.array([2059,2031,881,863,2279]))

y100_0 =[latency_100_500_0, latency_100_1000_0, latency_100_1500_0, latency_100_2000_0]

latency_100_500_1 = np.mean(np.array([649,527,562,1015,798]))
latency_100_1000_1 = np.mean(np.array([1118,1120,1104,1067,1041]))
latency_100_1500_1 = np.mean(np.array([1775,1514,1612,1680,1538]))
latency_100_2000_1 = np.mean(np.array([2194,2029,2083,2133,2247]))

y100_1 = [latency_100_500_1, latency_100_1000_1, latency_100_1500_1, latency_100_2000_1]

##
plt.figure(figsize=(14,8))
plt.plot(tle,y3_0,label = "alpha = 0")
plt.plot(tle,y3_01,label = "alpha = 0.1")
plt.plot(tle,y3_1,label = "alpha = 1")
plt.legend()

plt.ylabel("Latency (ms)")
plt.xlabel("timeout tle (s)")
plt.title("Graph representing the consensus latency in function of the timeout tle for N=3")
plt.savefig('N3.png')
plt.show()


plt.figure(figsize=(14,8))
plt.plot(tle,y10_0,label = "alpha = 0")
plt.plot(tle,y10_01,label = "alpha = 0.1")
plt.plot(tle,y10_1,label = "alpha = 1")
plt.legend()

plt.ylabel("Latency (ms)")
plt.xlabel("timeout tle (s)")
plt.title("Graph representing the consensus latency in function of the timeout tle for N=10")
plt.savefig('N10.png')
plt.show()

plt.figure(figsize=(14,8))
plt.plot(tle,y100_0,label = "alpha = 0")
plt.plot(tle,y100_01,label = "alpha = 0.1")
plt.plot(tle,y100_1,label = "alpha = 1")
plt.legend()

plt.ylabel("Latency (ms)")
plt.xlabel("timeout tle (s)")
plt.title("Graph representing the consensus latency in function of the timeout tle for N=100")
plt.savefig('N100.png')
plt.show()

####

y500_01 = [latency_3_500_01, latency_10_500_01, latency_100_500_01]
y500_0 = [latency_3_500_0, latency_10_500_0, latency_100_500_0]
y500_1 = [latency_3_500_1, latency_10_500_1, latency_100_500_1]

plt.figure(figsize=(14,8))
plt.plot(N,y500_0,label = "alpha = 0")
plt.plot(N,y500_01,label = "alpha = 0.1")
plt.plot(N,y500_1,label = "alpha = 1")
plt.legend()

plt.ylabel("Latency (ms)")
plt.xlabel("number of processes")
plt.title("Graph representing the consensus latency in function of the number of processes for tle = 0.5s")
#plt.savefig('tle500.png')
plt.show()

y1000_01 = [latency_3_1000_01, latency_10_1000_01, latency_100_1000_01]
y1000_0 = [latency_3_1000_0, latency_10_1000_0, latency_100_1000_0]
y1000_1 = [latency_3_1000_1, latency_10_1000_1, latency_100_1000_1]

plt.figure(figsize=(14,8))
plt.plot(N,y1000_0,label = "alpha = 0")
plt.plot(N,y1000_01,label = "alpha = 0.1")
plt.plot(N,y1000_1,label = "alpha = 1")
plt.legend()

plt.ylabel("Latency (ms)")
plt.xlabel("number of processes")
plt.title("Graph representing the consensus latency in function of the number of processes for tle = 1s")
#plt.savefig('tle1000.png')
plt.show()


y1500_01 = [latency_3_1500_01, latency_10_1500_01, latency_100_1500_01]
y1500_0 = [latency_3_1500_0, latency_10_1500_0, latency_100_1500_0]
y1500_1 = [latency_3_1500_1, latency_10_1500_1, latency_100_1500_1]

plt.figure(figsize=(14,8))
plt.plot(N,y1500_0,label = "alpha = 0")
plt.plot(N,y1500_01,label = "alpha = 0.1")
plt.plot(N,y1500_1,label = "alpha = 1")
plt.legend()

plt.ylabel("Latency (ms)")
plt.xlabel("number of processes")
plt.title("Graph representing the consensus latency in function of the number of processes for tle = 1.5s")
#plt.savefig('tle1500.png')
plt.show()

y2000_01 = [latency_3_2000_01, latency_10_2000_01, latency_100_2000_01]
y2000_0 = [latency_3_2000_0, latency_10_2000_0, latency_100_2000_0]
y2000_1 = [latency_3_2000_1, latency_10_2000_1, latency_100_2000_1]

plt.figure(figsize=(14,8))
plt.plot(N,y2000_0,label = "alpha = 0")
plt.plot(N,y2000_01,label = "alpha = 0.1")
plt.plot(N,y2000_1,label = "alpha = 1")
plt.legend()

plt.ylabel("Latency (ms)")
plt.xlabel("number of processes")
plt.title("Graph representing the consensus latency in function of the number of processes for tle = 2s")
#plt.savefig('tle2000.png')
plt.show()




