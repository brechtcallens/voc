import math

# Test math.sqrt
print(math.sqrt(4))
print(math.sqrt(12.34))
print(math.sqrt(-0.0))

# Test math.fabs
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

# Test math.sin
print(math.sin(0))
print(math.sin(0.0))

print(math.sin(5))
print(math.sin(-5))

print(math.sin(5.0))
print(math.sin(-5.0))

print(math.sin(True))
print(math.sin(False))

print(math.sin("Hello world"))
