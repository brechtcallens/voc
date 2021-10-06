from .. utils import (
TranspileTestCase,
BuiltinFunctionTestCase,
SAMPLE_SUBSTITUTIONS
)


class SetTests(TranspileTestCase):
    def test_set_readonly_true(self):
        self.assertCodeExecution("""
            from datetime import date
            try:
                d = date(1970, 1, 1)
                d.year = 1971
            except Exception as e:
                print(type(e), ':', e)
            """)
    
    def test_set_readonly_default_false(self):
        self.assertCodeExecution("""
            import time
            time.altzone = -7100
            """)


class BuiltinSetFunctionTests(BuiltinFunctionTestCase, TranspileTestCase):
    functions = ["set"]

    not_implemented = [
        'test_bytes',
        'test_str',
    ]

    not_implemented_versions = {
    }

    is_flakey = [
        'test_dict',
    ]

    substitutions = {
        "{3, 1.2, True}": [
            "{1.2, 3, True}", "{True, 1.2, 3}", "{True, 3, 1.2}", "{3, True, 1.2}", "{1.2, True, 3}"
        ]
    }
    substitutions.update(SAMPLE_SUBSTITUTIONS)
