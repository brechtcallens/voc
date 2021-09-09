from ..utils import TranspileTestCase


class MathModuleTests(TranspileTestCase):

    @staticmethod
    def create_test(func_name, arg_list):
        return f"""
            import math
            try:
                print(math.{func_name}({arg_list}))
            except Exception as e:
                print(type(e), ':', e)
        """

    @staticmethod
    def create_test_sqrt(arg):
        return MathModuleTests.create_test("sqrt", arg)

    def test_sqrt_valid_values(self):
        self.assertCodeExecution(self.create_test_sqrt("4"))
        self.assertCodeExecution(self.create_test_sqrt("12.34"))
        self.assertCodeExecution(self.create_test_sqrt("-0.0"))

    def test_sqrt_invalid_values(self):
        self.assertCodeExecution(self.create_test_sqrt("-3.5"))
        self.assertCodeExecution(self.create_test_sqrt("-5"))

    def test_sqrt_special_values(self):
        self.assertCodeExecution(self.create_test_sqrt("float('inf')"))
        self.assertCodeExecution(self.create_test_sqrt("float('nan')"))
        self.assertCodeExecution(self.create_test_sqrt("float('-inf')"))

    def test_sqrt_invalid_type(self):
        self.assertCodeExecution(self.create_test_sqrt("'Hello'"))
    
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

    def test_fabs_string(self):
        self.assertCodeExecution("""
            import math
            try:
                print(math.fabs('string'))
            except Exception as e:
                print(type(e), ":", e)
            """)
