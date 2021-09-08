from ..utils import TranspileTestCase


class MathModuleTests(TranspileTestCase):

    def test_sin_zero(self):
        self.assertCodeExecution("""
            from math import sin
            print(sin(0))
            print(sin(0.0))
        """)

    def test_sin_positive__int_values(self):
        self.assertCodeExecution("""
            from math import sin
            print(sin(5))
        """)

    def test_sin_negative__int_values(self):
        self.assertCodeExecution("""
            from math import sin
            print(sin(-5))
        """)

    def test_sin_postive_float_values(self):
        self.assertCodeExecution("""
            from math import sin
            print(sin(5.0))
        """)

    def test_sin_negative_float_values(self):
        self.assertCodeExecution("""
            from math import sin
            print(sin(-5.0))
        """)

    def test_sin_boolean_values(self):
        self.assertCodeExecution("""
            from math import sin
            print(sin(True))
            print(sin(False))
        """)
    
    def test_string_input(self):
        self.assertCodeExecution("""
            from math import sin
            try:
                print(sin("Hello world"))
            except Exception as e:
                print(type(e), ':', e)
        """)
