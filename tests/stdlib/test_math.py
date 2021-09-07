from ..utils import TranspileTestCase


class MathModuleTests(TranspileTestCase):
    def test_fabs_negative(self):
        self.assertCodeExecution("""
            import math
            print(math.fabs(-1.4))
            """)

    def test_fabs_zero(self):
        self.assertCodeExecution("""
            import math
            print(math.fabs(0.0))
            """)

    def test_fabs_positive(self):
        self.assertCodeExecution("""
            import math
            print(math.fabs(1.4))
            """)
