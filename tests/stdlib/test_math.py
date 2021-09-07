from unittest import expectedFailure

from ..utils import TranspileTestCase


class MathModuleTests(TranspileTestCase):
    def test_fabs_float_negative(self):
        self.assertCodeExecution("""
            import math
            print(math.fabs(-1.4))
            """)

    def test_fabs_float_zero(self):
        self.assertCodeExecution("""
            import math
            print(math.fabs(0.0))
            """)

    def test_fabs_float_positive(self):
        self.assertCodeExecution("""
            import math
            print(math.fabs(1.4))
            """)
    
    def test_fabs_integer_negative(self):
        self.assertCodeExecution("""
            import math
            print(math.fabs(-1))
            """)

    def test_fabs_integer_zero(self):
        self.assertCodeExecution("""
            import math
            print(math.fabs(0))
            """)

    def test_fabs_integer_positive(self):
        self.assertCodeExecution("""
            import math
            print(math.fabs(1))
            """)

    @expectedFailure
    def test_fabs_string(self):
        self.assertCodeExecution("""
            import math
            print(math.fabs('string'))
            """)
