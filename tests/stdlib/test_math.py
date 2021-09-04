import sys

from unittest import expectedFailure

from ..utils import TranspileTestCase


class MathModuleTests(TranspileTestCase):
    def test_ceil(self):
        self.assertCodeExecution("""
            import math
            print(math.fabs(-1.4))
            """)