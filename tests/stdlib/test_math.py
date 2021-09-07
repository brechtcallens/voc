from ..utils import TranspileTestCase


class MathModuleTests(TranspileTestCase):
    def test_ceil_negative(self):
        self.assertCodeExecution("""
            import math
            print(math.fabs(-1.4))
            """)

    def test_ceil_zero(self):
        self.assertCodeExecution("""
            import math
            print(math.fabs(0.0))
            """)

    def test_ceil_positive(self):
        self.assertCodeExecution("""
            import math
            print(math.fabs(1.4))
            """)
