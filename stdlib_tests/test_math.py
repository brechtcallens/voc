import math

print(math.fabs(-1.4))
print(math.fabs(0.0))
print(math.fabs(1.4))

print(math.fabs(-1))
print(math.fabs(0))
print(math.fabs(1))

try:
    print(math.fabs('string'))
except Exception as e:
    print(type(e), ":", e)
