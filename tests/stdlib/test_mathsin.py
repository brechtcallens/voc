from ..utils import TranspileTestCase


class MathSinTests(TranspileTestCase):

    def test_sin_zero(self):
        self.assertCodeExecution("""
            from math import sin
            x = sin(0)
            print('x =', x) 

            y = sin(0.0)       
            print('y =', y)
        """
        )

    def test_sin_positive__int_values(self):

        self.assertCodeExecution("""
            from math import sin
            x = sin(5)
            print('x =', x)
        """
        )

    def test_sin_negative__int_values(self):

        self.assertCodeExecution("""
            from math import sin
            x = sin(-5)
            print('x =', x)
        """
        )

    def test_sin_postive_float_values(self):
        self.assertCodeExecution("""
            from math import sin
            x = sin(5)
            print('x =', x)
        """
        )

    def test_sin_negative_float_values(self):
        self.assertCodeExecution("""
            from math import sin
            x = sin(5.0)
            print('x =', x)
        """
        )

    
    def test_sin_boolean_boolean_values(self):
        self.assertCodeExecution("""
            from math import sin
            x = sin(-5.0)
            print('x =', x)
        """
        )
    
    