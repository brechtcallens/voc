from ..utils import TranspileTestCase


class MathModuleTests(TranspileTestCase):

    def test_sqrt(self):
        self.assertCodeExecution("""
            import math
            print(math.sqrt(4))
        """)
